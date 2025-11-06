package com.pece.agencia.api.repository;

import com.pece.agencia.api.domain.Contratacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContratacaoRepository extends JpaRepository<Contratacao, UUID> {
    @Query(value = "select c from Contratacao c where c.cliente.id = :clientId")
    Page<Contratacao> findAllByCliente(UUID clientId, Pageable pageable);

    @Query(value = "select c from Contratacao c where c.pacoteContratado.id = :pacoteId")
    Page<Contratacao> findAllByPacote(UUID pacoteId, Pageable pageable);
}
