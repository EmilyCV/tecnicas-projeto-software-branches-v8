package com.pece.agencia.api.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "HOTEL_PARCEIRO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HotelParceiro extends Parceiro {
    @Column(name = "ID_PLATAFORMA")
    private String idPlataforma;
    @ManyToOne
    @JoinColumn(name = "ENDERECO_ID")
    private Endereco endereco;

}

