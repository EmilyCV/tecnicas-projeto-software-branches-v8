package com.pece.agencia.api.service;

import com.pece.agencia.api.domain.Cliente;
import com.pece.agencia.api.domain.Contratacao;
import com.pece.agencia.api.repository.ClienteRepository;
import com.pece.agencia.api.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ContratacaoRepository contratacaoRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ContratacaoRepository contratacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.contratacaoRepository = contratacaoRepository;
    }

    public Page<Contratacao> findAllCompras(UUID clientId, Pageable pageable) {
        return this.contratacaoRepository.findAllByCliente(clientId, pageable);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(UUID id) {
        return clienteRepository.findById(id);
    }

    public Page<Cliente> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }


    public Optional<Contratacao> findCompraById(UUID idCliente, UUID idCompra) {
        return this.contratacaoRepository.findById(idCompra)
                                    .filter(c -> c.getCliente().getId().equals(idCliente));
    }
}

