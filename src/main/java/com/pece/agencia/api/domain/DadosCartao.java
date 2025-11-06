package com.pece.agencia.api.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.YearMonth;

@Data
public class DadosCartao {
    @NotNull
    private String numero;
    @NotNull
    private String cvc;
    @NotNull
    private YearMonth dataExpiracao;
}
