package com.pece.agencia.api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "LOCADORA_VEICULO_PARCEIRA")
@Data
@EqualsAndHashCode(callSuper = true)
public class LocadoraVeiculoParceira extends Parceiro {
}
