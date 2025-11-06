package com.pece.agencia.api.domain;

import com.pece.agencia.api.domain.hibernate.UuidV7BasedID;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "ENDERECO")
@Data
public class Endereco {
    @Id
    @UuidV7BasedID
    @Column(name = "ID")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;
    @Column(name = "CEP")
    private String cep;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCALIDADE_ID")
    private Localidade localidade;
    @Column(name = "LOGRADOURO")
    private String logradouro;
    @Column(name = "NUMERO")
    private String numero;
    @Column(name = "COMPLEMENTO")
    private String complemento;
    @Column(name = "BAIRRO")
    private String bairro;
}
