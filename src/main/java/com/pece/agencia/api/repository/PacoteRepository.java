package com.pece.agencia.api.repository;

import com.pece.agencia.api.domain.Pacote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PacoteRepository extends JpaRepository<Pacote, UUID> {
}
