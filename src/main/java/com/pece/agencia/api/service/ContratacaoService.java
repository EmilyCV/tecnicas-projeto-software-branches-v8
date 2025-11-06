package com.pece.agencia.api.service;

import com.pece.agencia.api.domain.*;
import com.pece.agencia.api.repository.ContratacaoRepository;
import jakarta.validation.constraints.Future;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class ContratacaoService {
    private final ContratacaoRepository contratacaoRepository;
    private final PacoteService pacoteService;
    private final ClienteService clienteService;
    private final StripeService stripeService;
    private final ReservaHospedagemService reservaHospedagemService;
    private final LocacaoVeiculoService locacaoVeiculoService;
    private final ReservaTransladoAereoService reservaTransladoAereoService;

    public ContratacaoService(ContratacaoRepository contratacaoRepository, PacoteService pacoteService, ClienteService clienteService, StripeService stripeService, ReservaHospedagemService reservaHospedagemService, LocacaoVeiculoService locacaoVeiculoService, ReservaTransladoAereoService reservaTransladoAereoService) {
        this.contratacaoRepository = contratacaoRepository;
        this.pacoteService = pacoteService;
        this.clienteService = clienteService;
        this.stripeService = stripeService;
        this.reservaHospedagemService = reservaHospedagemService;
        this.locacaoVeiculoService = locacaoVeiculoService;
        this.reservaTransladoAereoService = reservaTransladoAereoService;
    }

    public Optional<Contratacao> findById(UUID id) {
        return this.contratacaoRepository.findById(id);
    }



    @Transactional
    public Contratacao contratar(UUID pacoteId, UUID clientId, DadosCartao dadosCartao, @Future LocalDate dataIda) throws Exception {
        Cliente cliente = this.clienteService.findById(clientId).get();
        Pacote pacote = this.pacoteService.findById(pacoteId).orElseThrow(() -> new Exception("Pacote não encontrado"));

        OfertaTransladoAereo ofertaTransladoAereo = pacote.ofertaDoTipo(OfertaTransladoAereo.class);

        Contratacao contratacao = cliente.contratar(pacote, dataIda);
        validar(contratacao);

        contratacao.setStripeChargeId(this.stripeService.pagar(dadosCartao, contratacao.getValorPago()));
        contratacao.setReservaHotel(this.reservaHospedagemService.reservar(contratacao.getPacoteContratado().ofertaDoTipo(OfertaHospedagem.class), cliente, contratacao.getPeriodoViagem()));
        contratacao.setLocalizadorReservaVeiculo(this.locacaoVeiculoService.locar(contratacao.getPacoteContratado().ofertaDoTipo(OfertaLocacaoVeiculo.class), cliente, pacote.getDestino(), contratacao.getPeriodoViagem()));

        contratacao.setReservaVooIda(this.reservaTransladoAereoService.reservar(contratacao.getCliente(), ofertaTransladoAereo.getVooIda(), contratacao.getPeriodoViagem().getInicio()));
        contratacao.setReservaVooVolta(this.reservaTransladoAereoService.reservar(contratacao.getCliente(), ofertaTransladoAereo.getVooVolta(), contratacao.getPeriodoViagem().getFim()));

        this.contratacaoRepository.save(contratacao);

        return contratacao;
    }

    private void validar(Contratacao contratacao) throws Exception {
        contratacao.getPacoteContratado().garantirDisponibilidade();

        if (contratacao.getPacoteContratado().expiradoEm(contratacao.getMomentoCompra())) {
            throw new Exception("Pacote " + contratacao.getPacoteContratado().getDescricao() + " fora da validade");
        }
    }
}
