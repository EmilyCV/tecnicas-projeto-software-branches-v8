package com.pece.agencia.api.service;

import com.pece.agencia.api.domain.Contratacao;
import com.pece.agencia.api.domain.Pacote;
import com.pece.agencia.api.repository.ContratacaoRepository;
import com.pece.agencia.api.repository.PacoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PacoteService {
    private final PacoteRepository pacoteRepository;
    private final ContratacaoRepository contratacaoRepository;


    @Autowired
    public PacoteService(PacoteRepository pacoteRepository, ContratacaoRepository contratacaoRepository) {
        this.pacoteRepository = pacoteRepository;
        this.contratacaoRepository = contratacaoRepository;
    }

    public List<Pacote> findAll() {
        return pacoteRepository.findAll();
    }

    public Optional<Pacote> findById(UUID id) {
        return pacoteRepository.findById(id);
    }

    public Page<Pacote> findAll(Pageable pageable) {
        return pacoteRepository.findAll(pageable);
    }

    public Page<Contratacao> findAllCompras(UUID pacoteId, Pageable pageable) {
        return this.contratacaoRepository.findAllByPacote(pacoteId, pageable);
    }

    public Optional<Contratacao> findCompraById(UUID idPacote, UUID idCompra) {
        return this.contratacaoRepository.findById(idCompra)
                .filter(c -> c.getPacoteContratado().getId().equals(idPacote));

    }
}

