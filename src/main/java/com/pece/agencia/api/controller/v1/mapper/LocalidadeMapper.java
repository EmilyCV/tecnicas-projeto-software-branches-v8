package com.pece.agencia.api.controller.v1.mapper;

import com.pece.agencia.api.domain.Localidade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocalidadeMapper {
    @Mapping(source = "codigoPlataformaLocacaoVeiculo", target = "codigoLocadoraVeiculo")
    com.pece.agencia.api.controller.v1.dto.Localidade toDTO(Localidade entity);
}
