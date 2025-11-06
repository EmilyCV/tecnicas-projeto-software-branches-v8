package com.pece.agencia.api.domain;

import com.pece.agencia.api.domain.hibernate.UuidV7BasedID;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "RESERVA_VOO")
@Data
public class ReservaVoo {

    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "ETICKET")
    private String eTicket;

    @Column(name = "ASSENTO")
    private String assento;

    @Column(name = "HORARIO_EMBARQUE")
    private LocalDateTime horarioEmbarque;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DADOS_VOO_ID")
    private DadosVoo dadosVoo;
}
