package com.pece.agencia.api.controller.v1.mapper;

import com.pece.agencia.api.domain.OfertaHospedagem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = EnderecoMapper.class
)
public interface ReservaHotelMapper {
    @Mapping(source = "parceiroResponsavel.nome", target = "nomeHotel")
    @Mapping(source = "parceiroResponsavel.endereco", target = "endereco")
    @Mapping(source = "parceiroResponsavel.emailContato", target = "email")
    @Mapping(source = "parceiroResponsavel.telefoneContato", target = "telefone")
    @Mapping(source = "parceiroResponsavel.idPlataforma", target = "idPlataforma")
    com.pece.agencia.api.controller.v1.dto.ReservaHotel toDTO(OfertaHospedagem entity);
}
