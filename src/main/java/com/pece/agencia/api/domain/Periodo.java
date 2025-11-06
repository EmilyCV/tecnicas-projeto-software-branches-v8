package com.pece.agencia.api.domain;

import com.pece.agencia.api.domain.hibernate.UuidV7BasedID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "PERIODO")
@Data
public class Periodo {

    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "INICIO")
    private LocalDate inicio;

    @Column(name = "FIM")
    private LocalDate fim;

    public boolean noRange(LocalDate date) {
        return !date.isBefore(this.inicio) && !date.isAfter(this.fim);
    }
}
