package com.pece.agencia.api.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "OFERTA_HOSPEDAGEM")
@Data
@EqualsAndHashCode(callSuper = true)
public class OfertaHospedagem extends Oferta<HotelParceiro> {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "HOTEL_PARCEIRO_ID")
    private HotelParceiro parceiroResponsavel;
}
