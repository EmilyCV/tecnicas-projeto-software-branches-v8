package com.pece.agencia.api.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "OFERTA_LOCACAO_VEICULO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OfertaLocacaoVeiculo extends Oferta<LocadoraVeiculoParceira> {
    @Column(name = "CATEGORIA_VEICULO")
    private String categoriaVeiculo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOCADORA_VEICULO_PARCEIRA_ID")
    private LocadoraVeiculoParceira parceiroResponsavel;
}
