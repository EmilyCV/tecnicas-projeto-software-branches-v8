package com.pece.agencia.api.controller.v1.mapper;

import com.pece.agencia.api.domain.Cliente;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = EnderecoMapper.class
)
public interface ClienteMapper {
    com.pece.agencia.api.controller.v1.dto.Cliente toDTO(Cliente entity);
}
