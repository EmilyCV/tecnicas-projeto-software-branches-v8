package com.pece.agencia.api.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "COMPANHIA_AEREA_PARCEIRA")
@Data
@EqualsAndHashCode(callSuper = true)
public class CompanhiaAereaParceira extends Parceiro {
}
