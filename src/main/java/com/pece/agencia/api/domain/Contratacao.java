package com.pece.agencia.api.domain;

import com.pece.agencia.api.domain.hibernate.UuidV7BasedID;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "CONTRATACAO")
@Data
public class Contratacao {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;
    @ManyToOne
    @JoinColumn(name="CLIENTE_ID")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name="PACOTE_CONTRATADO_ID")
    private Pacote pacoteContratado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PERIODO_VIAGEM_ID")
    private Periodo periodoViagem;

    @Column(name = "MOMENTO_COMPRA")
    private LocalDate momentoCompra;
    @Column(name = "VALOR_TOTAL")
    private double valorTotal;
    @Column(name = "VALOR_DESCONTO")
    private double valorDesconto;
    @Column(name = "VALOR_PAGO")
    private double valorPago;
    @Column(name = "STRIPE_CHARGE_ID")
    private String stripeChargeId;
    @Column(name = "RESERVA_HOTEL")
    private String reservaHotel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESERVA_VOO_IDA_ID")
    private ReservaVoo reservaVooIda;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESERVA_VOO_VOLTA_ID")
    private ReservaVoo reservaVooVolta;

    @Column(name = "LOCALIZADOR_RESERVA_VEICULO")
    private String localizadorReservaVeiculo;

    protected Contratacao() {
        // Para o hibernate
    }
    public Contratacao(Cliente cliente, Pacote pacoteContratado, LocalDate inicioViagem) throws Exception {
        this.cliente = cliente;
        this.pacoteContratado = pacoteContratado;
        this.momentoCompra = LocalDate.now();
        this.valorPago = pacoteContratado.getValorTotalAPagar();
        this.valorDesconto = pacoteContratado.getValorDescontoPromocional();
        this.valorTotal = pacoteContratado.getPrecoBase();
        this.periodoViagem = this.pacoteContratado.periodoViagemIniciandoEm(inicioViagem);
    }
}
