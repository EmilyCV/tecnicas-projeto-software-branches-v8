package com.pece.agencia.api.domain;

import com.pece.agencia.api.domain.hibernate.UuidV7BasedID;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "CLIENTE")
@Data
public class Cliente {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TELEFONE")
    private String telefone;
    @ManyToOne(fetch = FetchType.EAGER)
    private Endereco endereco;

    public Contratacao contratar(Pacote pacote, LocalDate inicioViagem) throws Exception {
        Contratacao contratacao = new Contratacao(this, pacote, inicioViagem);
        return contratacao;
    }
}
