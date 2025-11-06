package com.pece.agencia.api.service;

import com.pece.agencia.api.domain.Cliente;
import com.pece.agencia.api.domain.Localidade;
import com.pece.agencia.api.domain.OfertaLocacaoVeiculo;
import com.pece.agencia.api.domain.Periodo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LocacaoVeiculoService {

    @Value("${plataforma.locadora-veiculos.url}")
    private String plataformaLocadoraVeculosBaseUrl;

    public String locar(OfertaLocacaoVeiculo ofertaLocacaoVeiculo, Cliente motorista, Localidade destino, Periodo periodoViagem) {
        Map<String, String> request = new HashMap<>();
        request.put("motorista", motorista.getNome());
        request.put("categoria", ofertaLocacaoVeiculo.getCategoriaVeiculo());
        request.put("codigo-cidade", destino.getCodigoPlataformaLocacaoVeiculo());
        request.put("data-check-in", periodoViagem.getInicio().toString());
        request.put("data-check-out", periodoViagem.getFim().toString());

        RestTemplate template = new RestTemplate();
        Map<String, String> result = template.postForObject(plataformaLocadoraVeculosBaseUrl + "/api/v1/veiculos/reservas", request, Map.class);

        return result.get("numero-reserva");
    }

}
