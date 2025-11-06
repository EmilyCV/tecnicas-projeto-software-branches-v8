package com.pece.agencia.api.domain;

import com.pece.agencia.api.domain.hibernate.UuidV7BasedID;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PACOTE")
@Data
public class Pacote {
    @Id
    @UuidV7BasedID
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "DESTINO_ID")
    private Localidade destino;

    @Column(name = "DISPONIBILIDADE")
    private int disponibilidade;

    @Column(name = "PERCENTUAL_DESCONTO")
    private double percentualDesconto;

    @OneToMany
    @JoinColumn(name = "PACOTE_ID")
    private List<Oferta> ofertas;

    @Column(name="DURACAO_VIAGEM")
    private int duracaoViagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_DESCONTO")
    private TipoDesconto tipoDesconto;

    @ManyToOne
    @JoinColumn(name = "VALIDADE_ID")
    private Periodo validade;

    public Periodo periodoViagemIniciandoEm(LocalDate dataIda) {
        Periodo periodoViagem = new Periodo();
        periodoViagem.setInicio(dataIda);
        periodoViagem.setFim(dataIda.plusDays(getDuracaoViagem()));
        return periodoViagem;
    }

    public boolean expiradoEm(LocalDate date) {
        return !this.validade.noRange(date);
    }

    public void garantirDisponibilidade() throws Exception {
        if (this.getDisponibilidade() > 0) {
            this.setDisponibilidade(this.getDisponibilidade() - 1);
        } else {
            throw new Exception("Não há disponibilidade para o pacote " + getDescricao());
        }
    }

    public double getValorDescontoPromocional() {
        return getValorDescontoPromocional(LocalDate.now());
    }

    public double getValorDescontoPromocional(LocalDate dataCompra) {
        if (this.tipoDesconto == TipoDesconto.FIXO) {
            return getPrecoBase() * percentualDesconto;
        } else if (this.tipoDesconto == TipoDesconto.POR_ANTECIPACAO) {
            long diasAntecedencia = java.time.temporal.ChronoUnit.DAYS.between(dataCompra, this.validade.getInicio());
            double descontoAjustado = Math.max((diasAntecedencia / 30.0) * percentualDesconto, percentualDesconto);
            return Math.max(0, descontoAjustado * percentualDesconto);
        } else {
            throw new IllegalArgumentException("Tipo de desconto desconhecido " + this.tipoDesconto);
        }
    }

    public <T extends Oferta> T ofertaDoTipo(Class<T> clazz) {
        return (T) ofertas.stream()
                        .filter(item -> clazz.isInstance(item))
                        .findFirst()
                        .orElse(null);
    }
    public double getValorTotalAPagar() {
        return getPrecoBase() - getValorDescontoPromocional();
    }

    public double getPrecoBase() {
        return ofertas.stream().mapToDouble(Oferta::getPreco).sum();
    }
}
