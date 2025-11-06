package com.pece.agencia.api.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "OFERTA_TRANSLADO_AEREO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OfertaTransladoAereo extends Oferta<CompanhiaAereaParceira> {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VOO_IDA_ID")
    private DadosVoo vooIda;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VOO_VOLTA_ID")
    private DadosVoo vooVolta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANHIA_AEREA_PARCEIRA_ID")
    private CompanhiaAereaParceira parceiroResponsavel;
}
