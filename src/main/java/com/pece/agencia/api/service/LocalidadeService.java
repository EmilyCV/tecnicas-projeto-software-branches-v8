package com.pece.agencia.api.service;

import com.pece.agencia.api.domain.Localidade;
import com.pece.agencia.api.repository.LocalidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocalidadeService {
    private final LocalidadeRepository localidadeRepository;

    @Autowired
    public LocalidadeService(LocalidadeRepository localidadeRepository) {
        this.localidadeRepository = localidadeRepository;
    }

    public List<Localidade> findAll() {
        return localidadeRepository.findAll();
    }

    public Optional<Localidade> findById(UUID id) {
        return localidadeRepository.findById(id);
    }

    public Page<Localidade> findAll(Pageable pageable) {
        return localidadeRepository.findAll(pageable);
    }
}
