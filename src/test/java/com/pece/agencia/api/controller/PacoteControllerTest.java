package com.pece.agencia.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pece.agencia.api.AbstractTest;
import com.pece.agencia.api.utils.TokenFor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
class PacoteControllerTest extends AbstractTest {

    public static final String BASE_URL = "/v1/pacotes";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    public static final int TOTAL_ITEMS = 555;

    @Nested
    @DisplayName("Contratacoes")
    class ContratacaoBasedTests {
        @Nested
        @DisplayName("Obter uma compra")
        class SingleEntityTest {
            public static final String PACOTE_ID = "35ec6833-53be-4f36-9b69-cbd4bd87a6d3";
            public static final String COMPRA_ID = "03640cdf-7fd8-4ae2-a3dd-cc5de6cec9bd";
            public static final String OUTRO_PACOTE_ID = "aa849c8b-42ca-4ad2-852f-f9c289618b41";
            public static final String OUTRA_COMPRA_ID = "2ad487b9-3c1a-4e12-81ae-65c61dd70d8c";

            @Test
            @DisplayName("Deve retornar compra de clinete caso o ID da compra seja valido")
            void deveMostarCompraDePacote(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + PACOTE_ID + "/compras/" + COMPRA_ID)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value("03640cdf-7fd8-4ae2-a3dd-cc5de6cec9bd"))
                        .andExpect(jsonPath("$.dataCompra").value("2024-06-27"))
                        .andExpect(jsonPath("$.dataIda").value("2024-11-10"))
                        .andExpect(jsonPath("$.dataVolta").value("2024-11-15"))
                        .andExpect(jsonPath("$.valorTotal").value(3986.1377))
                        .andExpect(jsonPath("$.desconto").value(0.18))
                        .andExpect(jsonPath("$.valorPago").value(4861.1435))
                        .andExpect(jsonPath("$.stripeChargeId").value("ch_f329eccd9dd14f14b89c848a"))
                        .andExpect(jsonPath("$.reservaHotel").value("RH1fa00c49"))
                        .andExpect(jsonPath("$.reservaVooIda").value("ETIc8675122ce"))
                        .andExpect(jsonPath("$.assentoVooIda").value("17-C"))
                        .andExpect(jsonPath("$.reservaVooVolta").value("ETV8053f0c40f"))
                        .andExpect(jsonPath("$.assentoVooVolta").value("34-D"))
                        .andExpect(jsonPath("$.reservaVeiculo").value("RV8960f17d"))
                        .andExpect(jsonPath("$.embarqueVooIda").value("2024-09-24T00:00:00"))
                        .andExpect(jsonPath("$.embarqueVooVolta").value("2025-07-30T00:00:00"))
                        // Cliente
                        .andExpect(jsonPath("$.cliente.id").value("9e6f343b-71fc-48a2-a52e-7188103aecf0"))
                        .andExpect(jsonPath("$.cliente.nome").value("Carlos Santos"))
                        .andExpect(jsonPath("$.cliente.dataNascimento").value("2003-05-02"))
                        .andExpect(jsonPath("$.cliente.email").value("carlos.santos86@email.com"))
                        .andExpect(jsonPath("$.cliente.telefone").value("(88) 99865-8914"))
                        .andExpect(jsonPath("$.cliente.endereco.id").value("febb369e-960a-4de0-b5e4-d60beb77f6b3"))
                        .andExpect(jsonPath("$.cliente.endereco.cep").value("39718504"))
                        .andExpect(jsonPath("$.cliente.endereco.logradouro").value("Avenida Dom Pedro II"))
                        .andExpect(jsonPath("$.cliente.endereco.numero").value("1635"))
                        .andExpect(jsonPath("$.cliente.endereco.complemento").value("Sala 302"))
                        .andExpect(jsonPath("$.cliente.endereco.bairro").value("Savassi"))
                        .andExpect(jsonPath("$.cliente.endereco.localidade.id").value("f1a62904-d784-49e4-af77-6f085917d482"))
                        .andExpect(jsonPath("$.cliente.endereco.localidade.nomeCidade").value("Manaus"))
                        .andExpect(jsonPath("$.cliente.endereco.localidade.estado").value("AM"))
                        .andExpect(jsonPath("$.cliente.endereco.localidade.codigoLocadoraVeiculo").value("AA2831AB"))
                        // Pacote
                        .andExpect(jsonPath("$.pacote.id").value("35ec6833-53be-4f36-9b69-cbd4bd87a6d3"))
                        .andExpect(jsonPath("$.pacote.nome").value("Retiro Espiritual em Diamantina"))
                        .andExpect(jsonPath("$.pacote.localidade.id").value("5b936770-56b7-40ba-b372-9f980906fcbb"))
                        .andExpect(jsonPath("$.pacote.localidade.nomeCidade").value("Diamantina"))
                        .andExpect(jsonPath("$.pacote.localidade.estado").value("MG"))
                        .andExpect(jsonPath("$.pacote.localidade.codigoLocadoraVeiculo").value("1BFFB83B"))
                        .andExpect(jsonPath("$.pacote.dataInicio").value("2024-11-01"))
                        .andExpect(jsonPath("$.pacote.dataFim").value("2024-11-08"))
                        .andExpect(jsonPath("$.pacote.disponibilidade").value(3))
                        .andExpect(jsonPath("$.pacote.desconto").value(0.3))
                        .andExpect(jsonPath("$.pacote.duracao").value(5))
                        .andExpect(jsonPath("$.pacote.tipoDesconto").value("FIXO"))
                        .andExpect(jsonPath("$.pacote.valorDesconto").value(277.5))
                        .andExpect(jsonPath("$.pacote.preco").value(925.0))
                        .andExpect(jsonPath("$.pacote.custo").value(647.5))
                        // Pacote items
                        .andExpect(jsonPath("$.pacote.items[0].type").value("HOTEL"))
                        .andExpect(jsonPath("$.pacote.items[0].id").value("30768b5b-1e37-427a-8804-b1129264456b"))
                        .andExpect(jsonPath("$.pacote.items[0].preco").value(450.0))
                        .andExpect(jsonPath("$.pacote.items[0].nomeHotel").value("Hotel das Palmeiras"))
                        .andExpect(jsonPath("$.pacote.items[0].endereco.id").value("449a3cc0-2c74-40f3-8fda-2a3840e65c92"))
                        .andExpect(jsonPath("$.pacote.items[0].endereco.cep").value("21892910"))
                        .andExpect(jsonPath("$.pacote.items[0].endereco.localidade.id").value("933243f9-4aec-4288-af69-4d6ecf0cc16b"))
                        .andExpect(jsonPath("$.pacote.items[0].endereco.localidade.nomeCidade").value("Curitiba"))
                        .andExpect(jsonPath("$.pacote.items[0].endereco.localidade.estado").value("PR"))
                        .andExpect(jsonPath("$.pacote.items[0].endereco.localidade.codigoLocadoraVeiculo").value("FEF47CEE"))
                        .andExpect(jsonPath("$.pacote.items[0].endereco.logradouro").value("Alameda São João"))
                        .andExpect(jsonPath("$.pacote.items[0].endereco.numero").value("695"))
                        .andExpect(jsonPath("$.pacote.items[0].endereco.complemento").value(""))
                        .andExpect(jsonPath("$.pacote.items[0].endereco.bairro").value("Pinheiros"))
                        .andExpect(jsonPath("$.pacote.items[0].email").value("reservas@hoteldaspalmeiras.com"))
                        .andExpect(jsonPath("$.pacote.items[0].telefone").value("(11) 98765-4321"))
                        .andExpect(jsonPath("$.pacote.items[0].idPlataforma").value(379872))
                        .andExpect(jsonPath("$.pacote.items[1].type").value("ALUGUEL_VEICULO"))
                        .andExpect(jsonPath("$.pacote.items[1].id").value("81c49a1e-a0ad-4dc3-82cf-44402a903120"))
                        .andExpect(jsonPath("$.pacote.items[1].preco").value(125.0))
                        .andExpect(jsonPath("$.pacote.items[1].nomeLocadora").value("Rota Livre"))
                        .andExpect(jsonPath("$.pacote.items[1].categoriaCarro").value("Econômico"))
                        .andExpect(jsonPath("$.pacote.items[1].email").value("contato@rotalivre.com.br"))
                        .andExpect(jsonPath("$.pacote.items[1].telefone").value("(11) 98765-4321"))
                        .andExpect(jsonPath("$.pacote.items[2].type").value("TRANSLADO_AEREO"))
                        .andExpect(jsonPath("$.pacote.items[2].id").value("f563b1e1-e386-4537-9ea9-1739876409de"))
                        .andExpect(jsonPath("$.pacote.items[2].preco").value(350.0))
                        .andExpect(jsonPath("$.pacote.items[2].nomeCompanhia").value("FlyExpress"))
                        .andExpect(jsonPath("$.pacote.items[2].vooIdaNumero").value("EX1234"))
                        .andExpect(jsonPath("$.pacote.items[2].vooVoltaNumero").value("EX5678"))
                        .andExpect(jsonPath("$.pacote.items[2].vooIdaHora").value("08:00:00"))
                        .andExpect(jsonPath("$.pacote.items[2].vooVoltaHora").value("10:30:00"));
            }

            @Test
            @DisplayName("Deve retornar erro 401 se não autenticado")
            void deveRetornarErro401SemToken() throws Exception {
                mockMvc.perform(get(BASE_URL + PACOTE_ID + "/compras/" + COMPRA_ID)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @DisplayName("Deve retornar erro 400 se o id do pacote não for UUID válido")
            void deveRetornarErro400IdPacoteInvalido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(get(BASE_URL + "/kkk" + "/compras/" + COMPRA_ID)
                                .header("Authorization", "Bearer " + token)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("Deve retornar erro 400 se o id da compra não for UUID válido")
            void deveRetornarErro400IdCompraInvalido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(get(BASE_URL + "/" + PACOTE_ID + "/compras/KKK")
                                .header("Authorization", "Bearer " + token)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("Deve retornar erro 404 se o id do pacote percenter ao outra compra")
            void deveRetornarErro404IdPacoteInvalido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(get(BASE_URL + "/" + OUTRO_PACOTE_ID + "/compras/" + COMPRA_ID)
                                .header("Authorization", "Bearer " + token)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }

            @Test
            @DisplayName("Deve retornar erro 404 se o id da compra percenter a outro pacote")
            void deveRetornarErro404IdCompraInvalido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(get(BASE_URL + "/" + PACOTE_ID + "/compras/" + OUTRA_COMPRA_ID)
                                .header("Authorization", "Bearer " + token)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("Obter multiplas contratacoes")
        class PagedBasedTest {
            public static final int TOTAL_CONTRATACOES = 37; // Ajuste conforme o dataset real
            public static final String PACOTE_ID = "35ec6833-53be-4f36-9b69-cbd4bd87a6d3";

            @Test
            @DisplayName("Deve retornar lista de compras do pacote paginada e validar atributos do primeiro item e header X-Total-Count")
            void deveListarComprasPacoteComPaginacaoPadrao(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(get(BASE_URL + "/" + PACOTE_ID + "/compras")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.header().string("X-Total-Count", String.valueOf(TOTAL_CONTRATACOES)))
                        .andExpect(jsonPath("$[0].id").value("03640cdf-7fd8-4ae2-a3dd-cc5de6cec9bd"))
                        .andExpect(jsonPath("$[0].dataCompra").value("2024-06-27"))
                        .andExpect(jsonPath("$[0].dataIda").value("2024-11-10"))
                        .andExpect(jsonPath("$[0].dataVolta").value("2024-11-15"))
                        .andExpect(jsonPath("$[0].valorTotal").value(3986.1377))
                        .andExpect(jsonPath("$[0].desconto").value(0.18))
                        .andExpect(jsonPath("$[0].valorPago").value(4861.1435))
                        .andExpect(jsonPath("$[0].stripeChargeId").value("ch_f329eccd9dd14f14b89c848a"))
                        .andExpect(jsonPath("$[0].reservaHotel").value("RH1fa00c49"))
                        .andExpect(jsonPath("$[0].reservaVooIda").value("ETIc8675122ce"))
                        .andExpect(jsonPath("$[0].assentoVooIda").value("17-C"))
                        .andExpect(jsonPath("$[0].reservaVooVolta").value("ETV8053f0c40f"))
                        .andExpect(jsonPath("$[0].assentoVooVolta").value("34-D"))
                        .andExpect(jsonPath("$[0].reservaVeiculo").value("RV8960f17d"))
                        .andExpect(jsonPath("$[0].embarqueVooIda").value("2024-09-24T00:00:00"))
                        .andExpect(jsonPath("$[0].embarqueVooVolta").value("2025-07-30T00:00:00"))
                        // Cliente
                        .andExpect(jsonPath("$[0].cliente.id").value("9e6f343b-71fc-48a2-a52e-7188103aecf0"))
                        .andExpect(jsonPath("$[0].cliente.nome").value("Carlos Santos"))
                        .andExpect(jsonPath("$[0].cliente.dataNascimento").value("2003-05-02"))
                        .andExpect(jsonPath("$[0].cliente.email").value("carlos.santos86@email.com"))
                        .andExpect(jsonPath("$[0].cliente.telefone").value("(88) 99865-8914"))
                        .andExpect(jsonPath("$[0].cliente.endereco.id").value("febb369e-960a-4de0-b5e4-d60beb77f6b3"))
                        .andExpect(jsonPath("$[0].cliente.endereco.cep").value("39718504"))
                        .andExpect(jsonPath("$[0].cliente.endereco.logradouro").value("Avenida Dom Pedro II"))
                        .andExpect(jsonPath("$[0].cliente.endereco.numero").value("1635"))
                        .andExpect(jsonPath("$[0].cliente.endereco.complemento").value("Sala 302"))
                        .andExpect(jsonPath("$[0].cliente.endereco.bairro").value("Savassi"))
                        .andExpect(jsonPath("$[0].cliente.endereco.localidade.id").value("f1a62904-d784-49e4-af77-6f085917d482"))
                        .andExpect(jsonPath("$[0].cliente.endereco.localidade.nomeCidade").value("Manaus"))
                        .andExpect(jsonPath("$[0].cliente.endereco.localidade.estado").value("AM"))
                        .andExpect(jsonPath("$[0].cliente.endereco.localidade.codigoLocadoraVeiculo").value("AA2831AB"))
                        // Pacote
                        .andExpect(jsonPath("$[0].pacote.id").value("35ec6833-53be-4f36-9b69-cbd4bd87a6d3"))
                        .andExpect(jsonPath("$[0].pacote.nome").value("Retiro Espiritual em Diamantina"))
                        .andExpect(jsonPath("$[0].pacote.localidade.id").value("5b936770-56b7-40ba-b372-9f980906fcbb"))
                        .andExpect(jsonPath("$[0].pacote.localidade.nomeCidade").value("Diamantina"))
                        .andExpect(jsonPath("$[0].pacote.localidade.estado").value("MG"))
                        .andExpect(jsonPath("$[0].pacote.localidade.codigoLocadoraVeiculo").value("1BFFB83B"))
                        .andExpect(jsonPath("$[0].pacote.dataInicio").value("2024-11-01"))
                        .andExpect(jsonPath("$[0].pacote.dataFim").value("2024-11-08"))
                        .andExpect(jsonPath("$[0].pacote.disponibilidade").value(3))
                        .andExpect(jsonPath("$[0].pacote.desconto").value(0.3))
                        .andExpect(jsonPath("$[0].pacote.duracao").value(5))
                        .andExpect(jsonPath("$[0].pacote.tipoDesconto").value("FIXO"))
                        .andExpect(jsonPath("$[0].pacote.valorDesconto").value(277.5))
                        .andExpect(jsonPath("$[0].pacote.preco").value(925.0))
                        .andExpect(jsonPath("$[0].pacote.custo").value(647.5))
                        // Pacote items
                        .andExpect(jsonPath("$[0].pacote.items[0].type").value("HOTEL"))
                        .andExpect(jsonPath("$[0].pacote.items[0].id").value("30768b5b-1e37-427a-8804-b1129264456b"))
                        .andExpect(jsonPath("$[0].pacote.items[0].preco").value(450.0))
                        .andExpect(jsonPath("$[0].pacote.items[0].nomeHotel").value("Hotel das Palmeiras"))
                        .andExpect(jsonPath("$[0].pacote.items[0].endereco.id").value("449a3cc0-2c74-40f3-8fda-2a3840e65c92"))
                        .andExpect(jsonPath("$[0].pacote.items[0].endereco.cep").value("21892910"))
                        .andExpect(jsonPath("$[0].pacote.items[0].endereco.localidade.id").value("933243f9-4aec-4288-af69-4d6ecf0cc16b"))
                        .andExpect(jsonPath("$[0].pacote.items[0].endereco.localidade.nomeCidade").value("Curitiba"))
                        .andExpect(jsonPath("$[0].pacote.items[0].endereco.localidade.estado").value("PR"))
                        .andExpect(jsonPath("$[0].pacote.items[0].endereco.localidade.codigoLocadoraVeiculo").value("FEF47CEE"))
                        .andExpect(jsonPath("$[0].pacote.items[0].endereco.logradouro").value("Alameda São João"))
                        .andExpect(jsonPath("$[0].pacote.items[0].endereco.numero").value("695"))
                        .andExpect(jsonPath("$[0].pacote.items[0].endereco.complemento").value(""))
                        .andExpect(jsonPath("$[0].pacote.items[0].endereco.bairro").value("Pinheiros"))
                        .andExpect(jsonPath("$[0].pacote.items[0].email").value("reservas@hoteldaspalmeiras.com"))
                        .andExpect(jsonPath("$[0].pacote.items[0].telefone").value("(11) 98765-4321"))
                        .andExpect(jsonPath("$[0].pacote.items[0].idPlataforma").value(379872))
                        .andExpect(jsonPath("$[0].pacote.items[1].type").value("ALUGUEL_VEICULO"))
                        .andExpect(jsonPath("$[0].pacote.items[1].id").value("81c49a1e-a0ad-4dc3-82cf-44402a903120"))
                        .andExpect(jsonPath("$[0].pacote.items[1].preco").value(125.0))
                        .andExpect(jsonPath("$[0].pacote.items[1].nomeLocadora").value("Rota Livre"))
                        .andExpect(jsonPath("$[0].pacote.items[1].categoriaCarro").value("Econômico"))
                        .andExpect(jsonPath("$[0].pacote.items[1].email").value("contato@rotalivre.com.br"))
                        .andExpect(jsonPath("$[0].pacote.items[1].telefone").value("(11) 98765-4321"))
                        .andExpect(jsonPath("$[0].pacote.items[2].type").value("TRANSLADO_AEREO"))
                        .andExpect(jsonPath("$[0].pacote.items[2].id").value("f563b1e1-e386-4537-9ea9-1739876409de"))
                        .andExpect(jsonPath("$[0].pacote.items[2].preco").value(350.0))
                        .andExpect(jsonPath("$[0].pacote.items[2].nomeCompanhia").value("FlyExpress"))
                        .andExpect(jsonPath("$[0].pacote.items[2].vooIdaNumero").value("EX1234"))
                        .andExpect(jsonPath("$[0].pacote.items[2].vooVoltaNumero").value("EX5678"))
                        .andExpect(jsonPath("$[0].pacote.items[2].vooIdaHora").value("08:00:00"))
                        .andExpect(jsonPath("$[0].pacote.items[2].vooVoltaHora").value("10:30:00"));
            }

            @Test
            @DisplayName("Deve retornar lista de compras do pacote com paginação customizada e validar quantidade de elementos e header X-Total-Count")
            void deveListarComprasClienteComPaginacaoCustomizada(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                int page = 1;
                int size = 3;
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" +PACOTE_ID + "/compras?page=" + page + "&size=" + size)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.header().string("X-Total-Count", String.valueOf(TOTAL_CONTRATACOES)))
                        .andReturn();
                String content = result.getResponse().getContentAsString();
                JsonNode root = objectMapper.readTree(content);
                assertThat(root.isArray()).isTrue();
                assertThat(root.size()).isEqualTo(size);
            }

            @Test
            @DisplayName("Deve retornar página vazia ao solicitar página fora do range")
            void deveRetornarPaginaVaziaForaDoRange(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                int page = 100;
                int size = 5;
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + PACOTE_ID + "/compras?page=" + page + "&size=" + size)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.header().string("X-Total-Count", String.valueOf(TOTAL_CONTRATACOES)))
                        .andReturn();
                String content = result.getResponse().getContentAsString();
                JsonNode root = objectMapper.readTree(content);
                assertThat(root.isArray()).isTrue();
                assertThat(root.size()).isEqualTo(0);
            }

            @Test
            @DisplayName("Deve retornar 200 ao passar parâmetros de paginação inválidos (comportamento padrão do Spring)")
            void deveRetornar200PaginacaoInvalida(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + PACOTE_ID + "/compras?page=-1&size=abc")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("Deve retornar erro 401 se não autenticado")
            void deveRetornarErro401SemToken() throws Exception {
                mockMvc.perform(get(BASE_URL + PACOTE_ID + "/compras")
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    @DisplayName("Obter multiplos pacotes")
    class PagedBasedTest {
        @Test
        @DisplayName("Deve retornar a lista de pacotes paginada e validar todos os atributos do primeiro pacote e o header X-Total-Count")
        void deveListarPacotesComPaginacaoPadrao(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            mockMvc.perform(get(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.header().string("X-Total-Count", String.valueOf(TOTAL_ITEMS)))
                    .andExpect(jsonPath("$", hasSize(20)))
                    // Ajuste os valores conforme o payload fornecido (Férias em Paraty como primeiro elemento)
                    .andExpect(jsonPath("$[0].id").value("0018e0da-d903-4181-862b-0127bae799ea"))
                    .andExpect(jsonPath("$[0].nome").value("Férias em Paraty"))
                    .andExpect(jsonPath("$[0].localidade.id").value("984b955a-2d32-4681-8763-fa0800c4a752"))
                    .andExpect(jsonPath("$[0].localidade.nomeCidade").value("Paraty"))
                    .andExpect(jsonPath("$[0].localidade.estado").value("RJ"))
                    .andExpect(jsonPath("$[0].localidade.codigoLocadoraVeiculo").value("B77DD450"))
                    .andExpect(jsonPath("$[0].dataInicio").value("2024-03-10"))
                    .andExpect(jsonPath("$[0].dataFim").value("2224-03-17"))
                    .andExpect(jsonPath("$[0].disponibilidade").value(8))
                    .andExpect(jsonPath("$[0].desconto").value(0.21))
                    .andExpect(jsonPath("$[0].items", hasSize(3)))
                    .andExpect(jsonPath("$[0].items[0].type").value("TRANSLADO_AEREO"))
                    .andExpect(jsonPath("$[0].items[0].id").value("48bbe9ad-892d-4aaf-a2a6-a193a6ea81c0"))
                    .andExpect(jsonPath("$[0].items[0].preco").value(350))
                    .andExpect(jsonPath("$[0].items[0].nomeCompanhia").value("FlyExpress"))
                    .andExpect(jsonPath("$[0].items[0].vooIdaNumero").value("EX1234"))
                    .andExpect(jsonPath("$[0].items[0].vooVoltaNumero").value("EX5678"))
                    .andExpect(jsonPath("$[0].items[0].vooIdaHora").value("08:00:00"))
                    .andExpect(jsonPath("$[0].items[0].vooVoltaHora").value("10:30:00"))
                    .andExpect(jsonPath("$[0].items[1].type").value("HOTEL"))
                    .andExpect(jsonPath("$[0].items[1].id").value("6e8df48a-db18-4b8a-b946-04963f693620"))
                    .andExpect(jsonPath("$[0].items[1].preco").value(450))
                    .andExpect(jsonPath("$[0].items[1].nomeHotel").value("Hotel das Palmeiras"))
                    .andExpect(jsonPath("$[0].items[1].endereco.id").value("449a3cc0-2c74-40f3-8fda-2a3840e65c92"))
                    .andExpect(jsonPath("$[0].items[1].endereco.cep").value("21892910"))
                    .andExpect(jsonPath("$[0].items[1].endereco.localidade.id").value("933243f9-4aec-4288-af69-4d6ecf0cc16b"))
                    .andExpect(jsonPath("$[0].items[1].endereco.localidade.nomeCidade").value("Curitiba"))
                    .andExpect(jsonPath("$[0].items[1].endereco.localidade.estado").value("PR"))
                    .andExpect(jsonPath("$[0].items[1].endereco.localidade.codigoLocadoraVeiculo").value("FEF47CEE"))
                    .andExpect(jsonPath("$[0].items[1].endereco.logradouro").value("Alameda São João"))
                    .andExpect(jsonPath("$[0].items[1].endereco.numero").value("695"))
                    .andExpect(jsonPath("$[0].items[1].endereco.complemento").value(""))
                    .andExpect(jsonPath("$[0].items[1].endereco.bairro").value("Pinheiros"))
                    .andExpect(jsonPath("$[0].items[1].email").value("reservas@hoteldaspalmeiras.com"))
                    .andExpect(jsonPath("$[0].items[1].telefone").value("(11) 98765-4321"))
                    .andExpect(jsonPath("$[0].items[1].idPlataforma").value(379872))
                    .andExpect(jsonPath("$[0].items[2].type").value("ALUGUEL_VEICULO"))
                    .andExpect(jsonPath("$[0].items[2].id").value("c1e1c207-2c27-41c7-8472-cf44c13f3f19"))
                    .andExpect(jsonPath("$[0].items[2].preco").value(125))
                    .andExpect(jsonPath("$[0].items[2].nomeLocadora").value("Rota Livre"))
                    .andExpect(jsonPath("$[0].items[2].categoriaCarro").value("Econômico"))
                    .andExpect(jsonPath("$[0].items[2].email").value("contato@rotalivre.com.br"))
                    .andExpect(jsonPath("$[0].items[2].telefone").value("(11) 98765-4321"))
                    .andExpect(jsonPath("$[0].duracao").value(15))
                    .andExpect(jsonPath("$[0].tipoDesconto").value("COMPRA_ANTECIPADA"))
                    .andExpect(jsonPath("$[0].custo").value(924.9559))
                    .andExpect(jsonPath("$[0].valorDesconto").value(0.04409999999999999))
                    .andExpect(jsonPath("$[0].preco").value(925));
        }

        @Test
        @DisplayName("Deve retornar a lista de pacotes com paginação customizada e validar quantidade de elementos e header X-Total-Count")
        void deveListarPacotesComPaginacaoCustomizada(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            int page = 1;
            int size = 5;
            MvcResult result = mockMvc.perform(get(BASE_URL + "?page=" + page + "&size=" + size)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.header().string("X-Total-Count", String.valueOf(TOTAL_ITEMS)))
                    .andReturn();
            String content = result.getResponse().getContentAsString();
            JsonNode root = objectMapper.readTree(content);
            assertThat(root.isArray()).isTrue();
            assertThat(root.size()).isEqualTo(size);
        }

        @Test
        @DisplayName("Deve retornar lista vazia ao solicitar página fora do intervalo")
        void deveRetornarListaVaziaParaPaginaForaDoIntervalo(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            int page = 200;
            int size = 20;
            mockMvc.perform(get(BASE_URL + "?page=" + page + "&size=" + size)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.header().string("X-Total-Count", String.valueOf(TOTAL_ITEMS)))
                    .andExpect(jsonPath("$", hasSize(0)));
        }

        @Test
        @DisplayName("Deve retornar erro ao tentar acessar sem autenticação")
        void deveRetornarErroSemAutenticacao() throws Exception {
            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("Deve retornar 200 ao passar parâmetros de paginação inválidos (comportamento padrão do Spring)")
        void deveRetornar200PaginacaoInvalida(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/v1/pacotes?page=-1&size=abc")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("Obter único pacote")
    class SingleEntityTest {
        @Test
        @DisplayName("Deve retornar um pacote por ID e validar todos os atributos (plataforma regular")
        void deveRetornarPacotePorIdRegular(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            String pacoteId = "0018e0da-d903-4181-862b-0127bae799ea"; // Ajuste conforme o dataset real
            mockMvc.perform(get(BASE_URL + "/" + pacoteId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value("0018e0da-d903-4181-862b-0127bae799ea"))
                    .andExpect(jsonPath("$.nome").value("Férias em Paraty"))
                    .andExpect(jsonPath("$.localidade.id").value("984b955a-2d32-4681-8763-fa0800c4a752"))
                    .andExpect(jsonPath("$.localidade.nomeCidade").value("Paraty"))
                    .andExpect(jsonPath("$.localidade.estado").value("RJ"))
                    .andExpect(jsonPath("$.localidade.codigoLocadoraVeiculo").value("B77DD450"))
                    .andExpect(jsonPath("$.dataInicio").value("2024-03-10"))
                    .andExpect(jsonPath("$.dataFim").value("2224-03-17"))
                    .andExpect(jsonPath("$.disponibilidade").value(8))
                    .andExpect(jsonPath("$.desconto").value(0.21))
                    .andExpect(jsonPath("$.items", hasSize(3)))
                    .andExpect(jsonPath("$.items[0].type").value("TRANSLADO_AEREO"))
                    .andExpect(jsonPath("$.items[0].id").value("48bbe9ad-892d-4aaf-a2a6-a193a6ea81c0"))
                    .andExpect(jsonPath("$.items[0].preco").value(350))
                    .andExpect(jsonPath("$.items[0].nomeCompanhia").value("FlyExpress"))
                    .andExpect(jsonPath("$.items[0].vooIdaNumero").value("EX1234"))
                    .andExpect(jsonPath("$.items[0].vooVoltaNumero").value("EX5678"))
                    .andExpect(jsonPath("$.items[0].vooIdaHora").value("08:00:00"))
                    .andExpect(jsonPath("$.items[0].vooVoltaHora").value("10:30:00"))
                    .andExpect(jsonPath("$.items[1].type").value("HOTEL"))
                    .andExpect(jsonPath("$.items[1].id").value("6e8df48a-db18-4b8a-b946-04963f693620"))
                    .andExpect(jsonPath("$.items[1].preco").value(450))
                    .andExpect(jsonPath("$.items[1].nomeHotel").value("Hotel das Palmeiras"))
                    .andExpect(jsonPath("$.items[1].endereco.id").value("449a3cc0-2c74-40f3-8fda-2a3840e65c92"))
                    .andExpect(jsonPath("$.items[1].endereco.cep").value("21892910"))
                    .andExpect(jsonPath("$.items[1].endereco.localidade.id").value("933243f9-4aec-4288-af69-4d6ecf0cc16b"))
                    .andExpect(jsonPath("$.items[1].endereco.localidade.nomeCidade").value("Curitiba"))
                    .andExpect(jsonPath("$.items[1].endereco.localidade.estado").value("PR"))
                    .andExpect(jsonPath("$.items[1].endereco.localidade.codigoLocadoraVeiculo").value("FEF47CEE"))
                    .andExpect(jsonPath("$.items[1].endereco.logradouro").value("Alameda São João"))
                    .andExpect(jsonPath("$.items[1].endereco.numero").value("695"))
                    .andExpect(jsonPath("$.items[1].endereco.complemento").value(""))
                    .andExpect(jsonPath("$.items[1].endereco.bairro").value("Pinheiros"))
                    .andExpect(jsonPath("$.items[1].email").value("reservas@hoteldaspalmeiras.com"))
                    .andExpect(jsonPath("$.items[1].telefone").value("(11) 98765-4321"))
                    .andExpect(jsonPath("$.items[1].idPlataforma").value(379872))
                    .andExpect(jsonPath("$.items[2].type").value("ALUGUEL_VEICULO"))
                    .andExpect(jsonPath("$.items[2].id").value("c1e1c207-2c27-41c7-8472-cf44c13f3f19"))
                    .andExpect(jsonPath("$.items[2].preco").value(125))
                    .andExpect(jsonPath("$.items[2].nomeLocadora").value("Rota Livre"))
                    .andExpect(jsonPath("$.items[2].categoriaCarro").value("Econômico"))
                    .andExpect(jsonPath("$.items[2].email").value("contato@rotalivre.com.br"))
                    .andExpect(jsonPath("$.items[2].telefone").value("(11) 98765-4321"))
                    .andExpect(jsonPath("$.duracao").value(15))
                    .andExpect(jsonPath("$.tipoDesconto").value("COMPRA_ANTECIPADA"))
                    .andExpect(jsonPath("$.custo").value(924.9559))
                    .andExpect(jsonPath("$.valorDesconto").value(0.04409999999999999))
                    .andExpect(jsonPath("$.preco").value(925));
        }

        @Test
        @DisplayName("Deve retornar um pacote por ID e validar todos os atributos (plataforma ng")
        void deveRetornarPacotePorIdNg(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            String pacoteId = "155f253c-5b03-4e0b-abce-eb35737a01e0"; // Ajuste conforme o dataset real
            mockMvc.perform(get(BASE_URL + "/" + pacoteId)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value("155f253c-5b03-4e0b-abce-eb35737a01e0"))
                    .andExpect(jsonPath("$.nome").value("Viagem à Itália brasileira"))
                    .andExpect(jsonPath("$.localidade.id").value("08a22b3b-293f-48bf-8e26-7c469b82a76b"))
                    .andExpect(jsonPath("$.localidade.nomeCidade").value("Foz do Iguaçu"))
                    .andExpect(jsonPath("$.localidade.estado").value("PR"))
                    .andExpect(jsonPath("$.localidade.codigoLocadoraVeiculo").value("14890490"))
                    .andExpect(jsonPath("$.dataInicio").value("2026-03-15"))
                    .andExpect(jsonPath("$.dataFim").value("2226-03-22"))
                    .andExpect(jsonPath("$.disponibilidade").value(4))
                    .andExpect(jsonPath("$.desconto").value(0.24))
                    .andExpect(jsonPath("$.items", hasSize(3)))
                    .andExpect(jsonPath("$.items[0].type").value("HOTEL"))
                    .andExpect(jsonPath("$.items[0].id").value("00b676f6-698e-4973-8d20-9b6bf90c05ce"))
                    .andExpect(jsonPath("$.items[0].preco").value(640))
                    .andExpect(jsonPath("$.items[0].nomeHotel").value("Hotel do Peixe"))
                    .andExpect(jsonPath("$.items[0].endereco.id").value("1c63d241-39fd-4804-bb65-fe5dc25fedc9"))
                    .andExpect(jsonPath("$.items[0].endereco.cep").value("88531234"))
                    .andExpect(jsonPath("$.items[0].endereco.localidade.id").value("d43066d2-5762-4fa1-af9a-2258511482f7"))
                    .andExpect(jsonPath("$.items[0].endereco.localidade.nomeCidade").value("Recife"))
                    .andExpect(jsonPath("$.items[0].endereco.localidade.estado").value("PE"))
                    .andExpect(jsonPath("$.items[0].endereco.localidade.codigoLocadoraVeiculo").value("FCFE5560"))
                    .andExpect(jsonPath("$.items[0].endereco.logradouro").value("Praça Brasil"))
                    .andExpect(jsonPath("$.items[0].endereco.numero").value("1573"))
                    .andExpect(jsonPath("$.items[0].endereco.complemento").value("Loja 5"))
                    .andExpect(jsonPath("$.items[0].endereco.bairro").value("Moema"))
                    .andExpect(jsonPath("$.items[0].email").value("reservas@hoteldopeixe.com"))
                    .andExpect(jsonPath("$.items[0].telefone").value("(92) 93456-7890"))
                    .andExpect(jsonPath("$.items[0].idPlataforma").value("38d209b6"))
                    .andExpect(jsonPath("$.items[1].type").value("ALUGUEL_VEICULO"))
                    .andExpect(jsonPath("$.items[1].id").value("5265729d-74c8-40fd-bb9e-38eb46c46a52"))
                    .andExpect(jsonPath("$.items[1].preco").value(160))
                    .andExpect(jsonPath("$.items[1].nomeLocadora").value("Roda Fácil"))
                    .andExpect(jsonPath("$.items[1].categoriaCarro").value("Intermediário"))
                    .andExpect(jsonPath("$.items[1].email").value("sac@rodafácil.com.br"))
                    .andExpect(jsonPath("$.items[1].telefone").value("(61) 94321-8765"))
                    .andExpect(jsonPath("$.items[2].type").value("TRANSLADO_AEREO"))
                    .andExpect(jsonPath("$.items[2].id").value("a066bf34-405f-4e30-85ff-a20b002aa94d"))
                    .andExpect(jsonPath("$.items[2].preco").value(350))
                    .andExpect(jsonPath("$.items[2].nomeCompanhia").value("FlyExpress"))
                    .andExpect(jsonPath("$.items[2].vooIdaNumero").value("EX1234"))
                    .andExpect(jsonPath("$.items[2].vooVoltaNumero").value("EX5678"))
                    .andExpect(jsonPath("$.items[2].vooIdaHora").value("08:00:00"))
                    .andExpect(jsonPath("$.items[2].vooVoltaHora").value("10:30:00"))
                    .andExpect(jsonPath("$.duracao").value(4))
                    .andExpect(jsonPath("$.tipoDesconto").value("FIXO"))
                    .andExpect(jsonPath("$.custo").value(874))
                    .andExpect(jsonPath("$.valorDesconto").value(276))
                    .andExpect(jsonPath("$.preco").value(1150));
        }

        @Test
        @DisplayName("Deve retornar erro para pacote inexistente")
        void deveRetornarErroParaPacoteInexistente(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            String pacoteId = "00000000-0000-0000-0000-000000000000";
            mockMvc.perform(get(BASE_URL + "/" + pacoteId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Deve retornar erro ao tentar acessar sem autenticação")
        void deveRetornarErroSemAutenticacao() throws Exception {
            String pacoteId = "a1b2c3d4-e5f6-7890-abcd-1234567890ab";
            mockMvc.perform(get(BASE_URL + "/" + pacoteId))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("Deve retornar erro para formato de ID inválido")
        void deveRetornarErroParaIdInvalido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            mockMvc.perform(get(BASE_URL + "/invalido")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Cenários de corner case")
    class CornerCasesTest {
        @Test
        @DisplayName("Deve retornar erro para token JWT inválido")
        void deveRetornarErroParaTokenInvalido() throws Exception {
            mockMvc.perform(get(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer INVALID_TOKEN"))
                    .andExpect(status().isUnauthorized());
        }
    }
}
