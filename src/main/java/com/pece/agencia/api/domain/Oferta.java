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
@Table(name = "OFERTA")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = OfertaHospedagem.class, name = "HOTEL"),
    @JsonSubTypes.Type(value = OfertaTransladoAereo.class, name = "TRANSLADO_AEREO"),
    @JsonSubTypes.Type(value = OfertaLocacaoVeiculo.class, name = "ALUGUEL_VEICULO")
})
@Data
public abstract class Oferta<TipoParceiro extends Parceiro> {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;
    @Column(name = "PRECO")
    private double preco;

    public abstract TipoParceiro getParceiroResponsavel();
    public abstract void setParceiroResponsavel(TipoParceiro parceiroResponsavel);
}

