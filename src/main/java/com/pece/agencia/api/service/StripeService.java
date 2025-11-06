package com.pece.agencia.api.service;

import com.pece.agencia.api.domain.DadosCartao;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.TokenCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    public String pagar(DadosCartao dadosCartao, double value) throws StripeException {
        TokenCreateParams.Card card = TokenCreateParams.Card.builder()
                .setNumber(dadosCartao.getNumero())
                .setExpMonth(String.valueOf(dadosCartao.getDataExpiracao().getMonthValue()))
                .setExpYear(String.valueOf(dadosCartao.getDataExpiracao().getYear()))
                .setCvc(dadosCartao.getCvc())
                .build();

        Token token = Token.create(TokenCreateParams.builder().setCard(card).build());

        ChargeCreateParams chargeParams = ChargeCreateParams.builder()
                .setDescription("Venda de Pacote")
                .setCurrency("brl")
                .setAmount((long)(value * 100)) // em centavos
                .setSource(token.getCard().getId())
                .build();
        Charge charge = Charge.create(chargeParams);
        return charge.getId();
    }
}
