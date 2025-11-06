package com.pece.agencia.api.controller.v1;

import com.pece.agencia.api.controller.v1.dto.Compra;
import com.pece.agencia.api.controller.v1.dto.Pacote;
import com.pece.agencia.api.controller.v1.mapper.CompraMapper;
import com.pece.agencia.api.controller.v1.mapper.PacoteMapper;
import com.pece.agencia.api.service.ClienteService;
import com.pece.agencia.api.service.PacoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PacoteApiDelegateImplementation implements PacoteApiDelegate {

    private final ClienteService clienteService;
    private final PacoteService pacoteService;
    private final CompraMapper compraMapper;
    private final PacoteMapper pacoteMapper;

    @Override
    public ResponseEntity<Compra> buscarCompraDePacotePorId(UUID idPacote, UUID idCompra) {
        return pacoteService.findCompraById(idPacote, idCompra)
                            .map(compraMapper::toDTO)
                            .map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Compra>> buscarComprasPorPacote(UUID id, Pageable pageable) {
        Page<Compra> page = pacoteService.findAllCompras(id, pageable)
                                         .map(compraMapper::toDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Override
    public ResponseEntity<Pacote> buscarPacotePorId(UUID id) {
        return pacoteService.findById(id)
                            .map(pacoteMapper::toDTO)
                            .map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Pacote>> listarTodosPacotes(Pageable pageable) {
        Page<Pacote> page = pacoteService.findAll(pageable).map(pacoteMapper::toDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(page.getTotalElements()));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
