package com.pece.agencia.api.service;

import com.pece.agencia.api.domain.Cliente;
import com.pece.agencia.api.domain.OfertaHospedagem;
import com.pece.agencia.api.domain.Periodo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReservaHospedagemService {
    @Value("${plataforma.hotel.regular.url}")
    private String plataformaHotelBaseUrl;

    public String reservar(OfertaHospedagem hospedagem, Cliente cliente, Periodo periodoViagem) {
        Map<String, String> request = new HashMap<>();
        request.put("hospede", cliente.getNome());
        request.put("data-check-in", periodoViagem.getInicio().toString());
        request.put("data-check-out", periodoViagem.getFim().toString());

        String idPlataforma = hospedagem.getParceiroResponsavel().getIdPlataforma();

        RestTemplate template = new RestTemplate();
        Map<String, String> result = template.postForObject(plataformaHotelBaseUrl + "/api/v1/hoteis/" + idPlataforma + "/reservas", request, Map.class);
        return result.get("numero-reserva");
    }
}
