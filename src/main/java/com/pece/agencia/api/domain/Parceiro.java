package com.pece.agencia.api.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pece.agencia.api.domain.hibernate.UuidV7BasedID;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "PARCEIRO")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HotelParceiro.class, name = "HOTEL"),
        @JsonSubTypes.Type(value = CompanhiaAereaParceira.class, name = "COMPANHIA_AEREA"),
        @JsonSubTypes.Type(value = LocadoraVeiculoParceira.class, name = "LOCADORA_VEICULO")
})
@Data
public abstract class Parceiro {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "TELEFONE_CONTATO")
    private String telefoneContato;

    @Column(name = "EMAIL_CONTATO")
    private String emailContato;
}

