package com.pece.agencia.api.controller;

import com.pece.agencia.api.AbstractTest;
import com.pece.agencia.api.domain.Contratacao;
import com.pece.agencia.api.domain.Pacote;
import com.pece.agencia.api.repository.ContratacaoRepository;
import com.pece.agencia.api.repository.PacoteRepository;
import com.pece.agencia.api.utils.TokenFor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ContratacaoControllerTest extends AbstractTest {

    @Autowired
    private ContratacaoRepository contratacaoRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private PacoteRepository  pacoteRepository;

    private void setDisponibilidade(String pacoteId, int disponibilidade) {
        transactionTemplate.executeWithoutResult((tx) -> {
            Pacote pacote = pacoteRepository.findById(UUID.fromString(pacoteId)).get();
            pacote.setDisponibilidade(disponibilidade);
            pacoteRepository.save(pacote);
        });
    }

    @AfterEach
    public void resetDisponibilidade() {
        setDisponibilidade("0018e0da-d903-4181-862b-0127bae799ea", 8);
        setDisponibilidade("2c57eafe-0fa9-4aa7-9b8c-fcaf558fcc93", 10);
    }


    // Helper to get the date 7 days from now in yyyy-MM-dd
    private String getDataIda() {
        return LocalDate.now().plusDays(7).format(DateTimeFormatter.ISO_DATE);
    }

    private @NotNull String validPayload() {
        return """
        {
          "dataIda": "%s",
          "cartao": {
            "numero": "4242424242424242",
            "cvc": "314",
            "validade": "2026-05"
          }
        }
        """.formatted(getDataIda());
    }

    @Nested
    @DisplayName("Contratacoes com sucesso")
    class SucessoTests {

        private int disponibilidadePacote(String id) {
            return transactionTemplate.execute((tx) -> {
                Pacote pacote = pacoteRepository.findById(UUID.fromString(id)).get();
                return pacote.getDisponibilidade();
            });
        }
        private void assertContratacaoResultPlataformaRegular(MvcResult result) {
            transactionTemplate.executeWithoutResult((tx) -> {
                String location = result.getResponse().getHeader("Location");
                String id = location.substring(location.lastIndexOf("/") + 1);
                assertContratacaoResultPlataformaRegular(id);
            });
        }

        private void assertContratacaoResultPlataformaRegular(String contratacaoId) {
            Contratacao contratacao = contratacaoRepository.findById(UUID.fromString(contratacaoId)).get();

            assertThat(contratacao.getId().toString()).isEqualTo(contratacaoId);
            assertThat(contratacao.getCliente().getId()).isEqualTo(UUID.fromString("1914289b-1a28-433b-b472-d22b06f18841"));
            assertThat(contratacao.getPacoteContratado().getId()).isEqualTo(UUID.fromString("0018e0da-d903-4181-862b-0127bae799ea"));
            assertThat(contratacao.getPeriodoViagem().getInicio()).isNotNull();
            assertThat(contratacao.getPeriodoViagem().getFim()).isEqualTo(contratacao.getPeriodoViagem().getInicio().plusDays(contratacao.getPacoteContratado().getDuracaoViagem()));
            assertThat(contratacao.getMomentoCompra()).isEqualTo(LocalDate.now());
            assertThat(contratacao.getValorTotal()).isEqualTo(contratacao.getPacoteContratado().getPrecoBase());
            assertThat(contratacao.getValorDesconto()).isEqualTo(contratacao.getPacoteContratado().getValorDescontoPromocional());
            assertThat(contratacao.getValorPago()).isEqualTo(contratacao.getPacoteContratado().getValorTotalAPagar());
            assertThat(contratacao.getStripeChargeId()).isNotBlank();
            assertThat(contratacao.getReservaHotel()).hasSizeLessThanOrEqualTo(12);
            assertThat(contratacao.getReservaVooIda().getETicket()).isNotBlank();
            assertThat(contratacao.getReservaVooIda().getAssento()).isNotBlank();
            assertThat(contratacao.getReservaVooIda().getHorarioEmbarque()).isNotNull();
            assertThat(contratacao.getReservaVooVolta().getETicket()).isNotBlank();
            assertThat(contratacao.getReservaVooVolta().getAssento()).isNotBlank();
            assertThat(contratacao.getReservaVooVolta().getHorarioEmbarque()).isNotNull();
            assertThat(contratacao.getLocalizadorReservaVeiculo()).isNotBlank();
        }

        private void assertContratacaoResultPlataformaNg(MvcResult result) {
            transactionTemplate.executeWithoutResult((tx) -> {
                String location = result.getResponse().getHeader("Location");
                String id = location.substring(location.lastIndexOf("/") + 1);
                assertContratacaoResultPlataformaNg(id);
            });
        }

        private void assertContratacaoResultPlataformaNg(String contratacaoId) {
            Contratacao contratacao = contratacaoRepository.findById(UUID.fromString(contratacaoId)).get();

            assertThat(contratacao.getId().toString()).isEqualTo(contratacaoId);
            assertThat(contratacao.getCliente().getId()).isEqualTo(UUID.fromString("1914289b-1a28-433b-b472-d22b06f18841"));
            assertThat(contratacao.getPacoteContratado().getId()).isEqualTo(UUID.fromString("2c57eafe-0fa9-4aa7-9b8c-fcaf558fcc93"));
            assertThat(contratacao.getPeriodoViagem().getInicio()).isNotNull();
            assertThat(contratacao.getPeriodoViagem().getFim()).isEqualTo(contratacao.getPeriodoViagem().getInicio().plusDays(contratacao.getPacoteContratado().getDuracaoViagem()));
            assertThat(contratacao.getMomentoCompra()).isEqualTo(LocalDate.now());
            assertThat(contratacao.getValorTotal()).isEqualTo(contratacao.getPacoteContratado().getPrecoBase());
            assertThat(contratacao.getValorDesconto()).isEqualTo(contratacao.getPacoteContratado().getValorDescontoPromocional());
            assertThat(contratacao.getValorPago()).isEqualTo(contratacao.getPacoteContratado().getValorTotalAPagar());
            assertThat(contratacao.getStripeChargeId()).isNotBlank();
            assertThat(contratacao.getReservaHotel()).hasSizeGreaterThan(20);
            assertThat(contratacao.getReservaVooIda().getETicket()).isNotBlank();
            assertThat(contratacao.getReservaVooIda().getAssento()).isNotBlank();
            assertThat(contratacao.getReservaVooIda().getHorarioEmbarque()).isNotNull();
            assertThat(contratacao.getReservaVooVolta().getETicket()).isNotBlank();
            assertThat(contratacao.getReservaVooVolta().getAssento()).isNotBlank();
            assertThat(contratacao.getReservaVooVolta().getHorarioEmbarque()).isNotNull();
            assertThat(contratacao.getLocalizadorReservaVeiculo()).isNotBlank();
        }

        @Test
        @DisplayName("Deve contratar pacote com sucesso usando cartão válido (plataforma regular)")
        void deveContratarPacoteComSucessoPlataformaRegular(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            assertThat(disponibilidadePacote("0018e0da-d903-4181-862b-0127bae799ea")).isEqualTo(8);

            mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(validPayload()))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"))
                    .andExpect(this::assertContratacaoResultPlataformaRegular);

            assertThat(disponibilidadePacote("0018e0da-d903-4181-862b-0127bae799ea")).isEqualTo(7);
        }

        @Test
        @DisplayName("Deve contratar pacote com sucesso usando cartão válido (plataforma ng)")
        void deveContratarPacoteComSucessoPlataformaNg(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            assertThat(disponibilidadePacote("2c57eafe-0fa9-4aa7-9b8c-fcaf558fcc93")).isEqualTo(10);

            mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/2c57eafe-0fa9-4aa7-9b8c-fcaf558fcc93/contratacoes")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(validPayload()))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"))
                    .andExpect(this::assertContratacaoResultPlataformaNg);

            assertThat(disponibilidadePacote("2c57eafe-0fa9-4aa7-9b8c-fcaf558fcc93")).isEqualTo(9);
        }
    }


    @Nested
    @DisplayName("Contratacoes com falhas")
    class FalhaTests {
        @Nested
        @DisplayName("Contratacoes com falhas de infra-estrutura")
        class FalhaInfraEstruturaTests {
            @Test
            @DisplayName("Deve retornar erro se stripe estiver fora do ar")
            void deveRetornarErroStripeFora(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                stripeToxyproxy.disable();

                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isServiceUnavailable())
                        .andExpect(header().exists("Retry-After"));
            }

            @Test
            @DisplayName("Deve retornar erro se plataforma de hotaleria regular estiver fora do ar")
            void deveRetornarErroPlataformaHotelariaRegularFora(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                plataformaHotelRegularToxyproxy.disable();

                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isServiceUnavailable())
                        .andExpect(header().exists("Retry-After"));
            }

            @Test
            @DisplayName("Deve retornar erro se plataforma de hotaleria ng estiver fora do ar")
            void deveRetornarErroPlataformaHotelariaNGFora(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                plataformaHotelNgToxyproxy.disable();

                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/2c57eafe-0fa9-4aa7-9b8c-fcaf558fcc93/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isServiceUnavailable())
                        .andExpect(header().exists("Retry-After"));
            }

            @Test
            @DisplayName("Deve retornar erro se plataforma de companhia aerea estiver fora do ar")
            void deveRetornarErroPlataformaCompanhiaAereaFora(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                plataformaCompanhiaAereaToxyproxy.disable();

                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isServiceUnavailable())
                        .andExpect(header().exists("Retry-After"));
            }

            @Test
            @DisplayName("Deve retornar erro se plataforma de locadora veiculos estiver fora do ar")
            void deveRetornarErroPlataformalocadoraVeiculosFora(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                plataformaLocadoraVeiculoToxyproxy.disable();

                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isServiceUnavailable())
                        .andExpect(header().exists("Retry-After"));
            }
        }


        @Nested
        @DisplayName("Contratacoes com falhas de negocios")
        class FalhaNegociosTests {

            @Test
            @DisplayName("Deve retornar erro ao tentar contratar pacote sem disponibilidade")
            void deveRetornarErroPacoteSemDisponibilidade(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/c56c9b84-c83d-4cec-8781-488d97af483d/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isUnprocessableEntity());
            }

            @Test
            @DisplayName("Deve retornar erro ao tentar contratar pacote já vencido")
            void deveRetornarErroPacoteVencido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {

                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/2836ad8e-f71f-4f4d-909b-973a2567384b/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isUnprocessableEntity());
            }

            @Test
            @DisplayName("Deve retornar erro ao contratar com cartao negado")
            void deveFalharAoUsarCartaoNegado(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                String payload = """
                        {
                          "dataIda": "%s",
                          "cartao": {
                            "numero": "4000008260003178",
                            "cvc": "314",
                            "validade": "2026-05"
                          }
                        }
                        """.formatted(getDataIda());
                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/ce507a91-29c9-42a9-b0d2-ba0a3295cea8/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload))
                        .andExpect(status().isPaymentRequired());
            }

            @Test
            @DisplayName("Deve retornar erro ao enviar payload sem campo obrigatório (cartao)")
            void deveRetornarErroPayloadSemCartao(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                String payload = """
                        {
                          "dataIda": "%s"
                        }
                        """.formatted(getDataIda());
                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("Deve retornar erro ao enviar data de ida no passado")
            void deveRetornarErroDataIdaNoPassado(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                String dataPassada = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE);
                String payload = """
                        {
                          "dataIda": "%s",
                          "cartao": {
                            "numero": "4242424242424242",
                            "cvc": "314",
                            "validade": "2026-05"
                          }
                        }
                        """.formatted(dataPassada);
                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("Deve retornar erro ao tentar contratar sem autenticação (sem token)")
            void deveRetornarErroSemToken() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @DisplayName("Deve retornar erro ao tentar contratar com token inválido")
            void deveRetornarErroTokenInvalido() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                .header("Authorization", "Bearer token_invalido")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isUnauthorized());
            }


            @Test
            @DisplayName("Deve retornar erro ao enviar payload com tipos errados (numero do cartão como inteiro)")
            void deveRetornarErroPayloadTiposErrados(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                String payload = """
                        {
                          "dataIda": "%s",
                          "cartao": {
                            "numero": 4242424242424242,
                            "cvc": "314",
                            "validade": "4242424242424242"
                          }
                        }
                        """.formatted(getDataIda());
                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/0018e0da-d903-4181-862b-0127bae799ea/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("Deve retornar erro ao tentar contratar pacote inexistente")
            void deveRetornarErroPacoteInexistente(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {

                mockMvc.perform(MockMvcRequestBuilders.post("/v1/pacotes/00000000-0000-0000-0000-000000000000/contratacoes")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validPayload()))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
