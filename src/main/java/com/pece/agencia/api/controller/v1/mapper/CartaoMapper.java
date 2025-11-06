package com.pece.agencia.api.controller.v1.mapper;


import com.pece.agencia.api.controller.v1.dto.Cartao;
import com.pece.agencia.api.domain.DadosCartao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.YearMonth;

@Mapper(
    componentModel = "spring"
)
public interface CartaoMapper {
    @Mapping(target="dataExpiracao", source = "validade")
    DadosCartao toDomain(Cartao dto);

    default YearMonth toDomain(String value) {
        return YearMonth.parse(value);
    }
}
