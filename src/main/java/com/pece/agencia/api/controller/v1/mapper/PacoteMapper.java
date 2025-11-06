package com.pece.agencia.api.controller.v1.mapper;

import com.pece.agencia.api.domain.Pacote;
import com.pece.agencia.api.domain.TipoDesconto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;

@Mapper(
    componentModel = "spring",
    uses = {
        ItemPacoteMapper.class,
        LocalidadeMapper.class
    }
)
public interface PacoteMapper {
    @Mapping(target = "nome", source = "descricao")
    @Mapping(target = "localidade", source = "destino")
    @Mapping(target = "items", source = "ofertas")
    @Mapping(target = "dataInicio", source = "validade.inicio")
    @Mapping(target = "dataFim", source = "validade.fim")
    @Mapping(target = "desconto", source = "percentualDesconto")
    @Mapping(target = "duracao", source = "duracaoViagem")
    @Mapping(target = "valorDesconto", source = "valorDescontoPromocional")
    @Mapping(target = "preco", source = "precoBase")
    @Mapping(target = "custo", source = "valorTotalAPagar")
    com.pece.agencia.api.controller.v1.dto.Pacote toDTO(Pacote entity);

    @ValueMapping(target = "COMPRA_ANTECIPADA", source = "POR_ANTECIPACAO")
    @ValueMapping(target = "FIXO", source = "FIXO")
    com.pece.agencia.api.controller.v1.dto.Pacote.TipoDescontoEnum toDto(TipoDesconto source);
}
