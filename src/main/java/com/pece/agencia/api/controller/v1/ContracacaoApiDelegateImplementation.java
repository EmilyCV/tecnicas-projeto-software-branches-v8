package com.pece.agencia.api.controller.v1;

import com.pece.agencia.api.controller.v1.dto.Compra;
import com.pece.agencia.api.controller.v1.dto.ContratacaoRequest;
import com.pece.agencia.api.controller.v1.mapper.CartaoMapper;
import com.pece.agencia.api.controller.v1.mapper.CompraMapper;
import com.pece.agencia.api.domain.Contratacao;
import com.pece.agencia.api.service.ContratacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContracacaoApiDelegateImplementation implements ContratacaoApiDelegate {
    private final ContratacaoService contratacaoService;
    private final CompraMapper compraMapper;
    private final CartaoMapper cartaoMapper;

    private Jwt loggedUser() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public ResponseEntity<Void> contratar(UUID pacoteId, ContratacaoRequest request) throws Exception {
        try {
            UUID clientId = UUID.fromString(loggedUser().getClaimAsString("sub"));
            Contratacao contratacao = contratacaoService.contratar(pacoteId, clientId, cartaoMapper.toDomain(request.getCartao()), request.getDataIda());

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(contratacao.getId()) // Assuming YourResource has an getId() method
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            // MUITO MUITO MUITO RUIM
            if (ex.getMessage().contains("Pacote não encontrado")) {
                return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), ex.getMessage())).build();
            } else if (ex.getMessage().contains("Não há disponibilidade para o pacote")) {
                return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(422), ex.getMessage())).build();
            } else if (ex.getMessage().contains("fora da validade")) {
                return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(422), ex.getMessage())).build();
            } else if (ex.getMessage().contains("card_declined")) {
                return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(402), ex.getMessage())).build();
            } else if (ex.getMessage().contains("IOException during API request to Stripe")) {
                return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(503), ex.getMessage())).header("Retry-After", "10").build();
            } else if (ex.getMessage().contains("I/O error on POST request for")) {
                return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(503), ex.getMessage())).header("Retry-After", "10").build();
            } else {
                throw ex;
            }
        }
    }

    @Override
    public ResponseEntity<Compra> buscarContratacaoPorId(UUID contratacaoId) {
        return contratacaoService.findById(contratacaoId)
                .map(compraMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
