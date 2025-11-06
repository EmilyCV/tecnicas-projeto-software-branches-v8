package com.pece.agencia.api.controller.v1.mapper;

import com.pece.agencia.api.domain.Contratacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
            ClienteMapper.class,
            PacoteMapper.class
        }
)
public interface CompraMapper {
    @Mapping(target = "reservaVooIda", source = "reservaVooIda.ETicket")
    @Mapping(target = "assentoVooIda", source = "reservaVooIda.assento")
    @Mapping(target = "embarqueVooIda", source = "reservaVooIda.horarioEmbarque")
    @Mapping(target = "reservaVooVolta", source = "reservaVooVolta.ETicket")
    @Mapping(target = "assentoVooVolta", source = "reservaVooVolta.assento")
    @Mapping(target = "embarqueVooVolta", source = "reservaVooVolta.horarioEmbarque")
    @Mapping(target = "pacote", source = "pacoteContratado")
    @Mapping(target = "dataIda", source = "periodoViagem.inicio")
    @Mapping(target = "dataVolta", source = "periodoViagem.fim")
    @Mapping(target = "reservaVeiculo", source = "localizadorReservaVeiculo")
    @Mapping(target = "desconto", source = "valorDesconto")
    @Mapping(target = "dataCompra", source = "momentoCompra")
    com.pece.agencia.api.controller.v1.dto.Compra toDTO(Contratacao entity);
}
