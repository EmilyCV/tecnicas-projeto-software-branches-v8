package com.pece.agencia.api.domain;

import com.pece.agencia.api.domain.hibernate.UuidV7BasedID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "DADOS_VOO")
@Data
public class DadosVoo {
    @Id
    @UuidV7BasedID
    @Column(name = "ID")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @Column(name = "NUMERO")
    private String numero;
    @Column(name = "HORARIO")
    private LocalTime horario;

    public DadosVoo clone() {
        DadosVoo clone = new DadosVoo();

        clone.setNumero(this.getNumero());
        clone.setHorario(this.getHorario());

        return clone;
    }
}
