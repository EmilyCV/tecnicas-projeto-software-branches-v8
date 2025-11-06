package com.pece.agencia.api.controller.v1.mapper;

import com.pece.agencia.api.domain.Endereco;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = LocalidadeMapper.class
)
public interface EnderecoMapper {
    com.pece.agencia.api.controller.v1.dto.Endereco toDTO(Endereco entity);
}
