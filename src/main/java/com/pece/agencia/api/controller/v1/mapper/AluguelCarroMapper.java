package com.pece.agencia.api.controller.v1.mapper;

import com.pece.agencia.api.domain.OfertaLocacaoVeiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring"
)
public interface AluguelCarroMapper {
    @Mapping(source = "parceiroResponsavel.nome", target = "nomeLocadora")
    @Mapping(source = "parceiroResponsavel.emailContato", target = "email")
    @Mapping(source = "parceiroResponsavel.telefoneContato", target = "telefone")
    @Mapping(source = "categoriaVeiculo", target = "categoriaCarro")
    com.pece.agencia.api.controller.v1.dto.AluguelCarro toDTO(OfertaLocacaoVeiculo entity);

}
