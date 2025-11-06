package com.pece.agencia.api.service;

import com.pece.agencia.api.domain.Cliente;
import com.pece.agencia.api.domain.DadosVoo;
import com.pece.agencia.api.domain.ReservaVoo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReservaTransladoAereoService {

    @Value("${plataforma.empresa-aerea.url}")
    private String plataformaEmpresaAereaBaseUrl;

    public ReservaVoo reservar(Cliente passageiro, DadosVoo dadosVoo, LocalDate data) {
        Map <String, String> resultadoReserva = this.doReservar(passageiro.getNome(), dadosVoo.getNumero(), data);

        ReservaVoo reservaVoo = new ReservaVoo();
        reservaVoo.setDadosVoo(dadosVoo.clone());
        reservaVoo.setHorarioEmbarque(data.atTime(dadosVoo.getHorario()));
        reservaVoo.setAssento(resultadoReserva.get("assento"));
        reservaVoo.setETicket(resultadoReserva.get("eticket"));

        return reservaVoo;
    }

    private Map<String, String> doReservar(String passageiro, String numeroVoo, LocalDate dataIda) {
        Map<String, String> request = new HashMap<>();
        request.put("passageiro", passageiro);
        request.put("data", dataIda.toString());

        RestTemplate template = new RestTemplate();
        Map<String, String> result = template.postForObject(plataformaEmpresaAereaBaseUrl + "/api/v1/voos/" + numeroVoo + "/reservas", request, Map.class);

        return result;
    }
}
