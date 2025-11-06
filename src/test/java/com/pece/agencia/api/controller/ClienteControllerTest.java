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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClienteControllerTest extends AbstractTest {


    public static final String BASE_URL = "/v1/clientes/";
    @Autowired
    private ObjectMapper objectMapper;

    public static final int TOTAL_ITEMS = 100; // Ajuste conforme o dataset real

    @Nested
    @DisplayName("Contratacoes")
    class ContratacaoBasedTests {
        @Nested
        @DisplayName("Obter uma compra")
        class SingleEntityTest {
            public static final String CLIENTE_ID = "ec2e748a-987d-4853-85d4-79ffaaf28b7b";
            public static final String COMPRA_ID = "010d5341-f221-4fd3-b6af-6ee124d8dc5c";
            public static final String OUTRO_CLIENTE_ID = "ca2e76bd-5911-478d-b7ba-022ba9a01b8a";
            public static final String OUTRA_COMPRA_ID = "06e4948d-0d62-47c7-8ff7-6cf0501b14d0";

            @Test
            @DisplayName("Deve retornar compra de cliente caso o ID da compra seja valido")
            void deveMostarCompraDeCliente(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + CLIENTE_ID + "/compras/" + COMPRA_ID)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value("010d5341-f221-4fd3-b6af-6ee124d8dc5c"))
                        .andExpect(jsonPath("$.dataCompra").value("2026-01-17"))
                        .andExpect(jsonPath("$.dataIda").value("2026-05-10"))
                        .andExpect(jsonPath("$.dataVolta").value("2026-05-24"))
                        .andExpect(jsonPath("$.valorTotal").value(1248.1124))
                        .andExpect(jsonPath("$.desconto").value(0.12))
                        .andExpect(jsonPath("$.valorPago").value(1418.3096))
                        .andExpect(jsonPath("$.stripeChargeId").value("ch_bdf4ef56a6854da18e1f9fce"))
                        .andExpect(jsonPath("$.reservaHotel").value("RH702dbfe6"))
                        .andExpect(jsonPath("$.reservaVooIda").value("ETI7c141f7bc2"))
                        .andExpect(jsonPath("$.assentoVooIda").value("38-F"))
                        .andExpect(jsonPath("$.reservaVooVolta").value("ETV79721bb934"))
                        .andExpect(jsonPath("$.assentoVooVolta").value("4-D"))
                        .andExpect(jsonPath("$.reservaVeiculo").value("RVd66ea549"))
                        .andExpect(jsonPath("$.embarqueVooIda").value("2026-02-13T00:00:00"))
                        .andExpect(jsonPath("$.embarqueVooVolta").value("2026-05-27T00:00:00"))
                        .andExpect(jsonPath("$.cliente.id").value("ec2e748a-987d-4853-85d4-79ffaaf28b7b"))
                        .andExpect(jsonPath("$.cliente.nome").value("Lucas Lima"))
                        .andExpect(jsonPath("$.cliente.dataNascimento").value("1997-09-18"))
                        .andExpect(jsonPath("$.cliente.email").value("lucas.lima78@mail.com"))
                        .andExpect(jsonPath("$.cliente.telefone").value("(82) 99802-0917"))
                        .andExpect(jsonPath("$.cliente.endereco.id").value("1b80d79e-166d-4753-adb8-7615f2dc398e"))
                        .andExpect(jsonPath("$.cliente.endereco.cep").value("56334960"))
                        .andExpect(jsonPath("$.cliente.endereco.logradouro").value("Avenida Dom Pedro II"))
                        .andExpect(jsonPath("$.cliente.endereco.numero").value("566"))
                        .andExpect(jsonPath("$.cliente.endereco.complemento").value("Fundos"))
                        .andExpect(jsonPath("$.cliente.endereco.bairro").value("Santa Cecília"))
                        .andExpect(jsonPath("$.cliente.endereco.localidade.id").value("0c6c715c-8c82-407d-8fc8-5d4fab0c8284"))
                        .andExpect(jsonPath("$.cliente.endereco.localidade.nomeCidade").value("Porto Alegre"))
                        .andExpect(jsonPath("$.cliente.endereco.localidade.estado").value("RS"))
                        .andExpect(jsonPath("$.cliente.endereco.localidade.codigoLocadoraVeiculo").value("C4668F0F"))
                        .andExpect(jsonPath("$.pacote.id").value("701d2fea-09c2-4a92-ab78-3e36b423e53c"))
                        .andExpect(jsonPath("$.pacote.nome").value("Viagem de Trem em Curitiba"))
                        .andExpect(jsonPath("$.pacote.localidade.id").value("933243f9-4aec-4288-af69-4d6ecf0cc16b"))
                        .andExpect(jsonPath("$.pacote.localidade.nomeCidade").value("Curitiba"))
                        .andExpect(jsonPath("$.pacote.localidade.estado").value("PR"))
                        .andExpect(jsonPath("$.pacote.localidade.codigoLocadoraVeiculo").value("FEF47CEE"))
                        .andExpect(jsonPath("$.pacote.dataInicio").value("2026-04-01"))
                        .andExpect(jsonPath("$.pacote.dataFim").value("2226-04-08"))
                        .andExpect(jsonPath("$.pacote.disponibilidade").value(2))
                        .andExpect(jsonPath("$.pacote.desconto").value(0.3))
                        .andExpect(jsonPath("$.pacote.duracao").value(7))
                        .andExpect(jsonPath("$.pacote.tipoDesconto").value("FIXO"))
                        .andExpect(jsonPath("$.pacote.valorDesconto").value(327.0))
                        .andExpect(jsonPath("$.pacote.preco").value(1090.0))
                        .andExpect(jsonPath("$.pacote.custo").value(763.0))
                        .andExpect(jsonPath("$.pacote.items[0].type").value("ALUGUEL_VEICULO"))
                        .andExpect(jsonPath("$.pacote.items[0].id").value("0f00d712-afe9-4af2-a33d-e2c11dc97217"))
                        .andExpect(jsonPath("$.pacote.items[0].preco").value(150.0))
                        .andExpect(jsonPath("$.pacote.items[0].nomeLocadora").value("Quilômetro Certo"))
                        .andExpect(jsonPath("$.pacote.items[0].categoriaCarro").value("SUV"))
                        .andExpect(jsonPath("$.pacote.items[0].email").value("atendimento@quilometrocerto.com.br"))
                        .andExpect(jsonPath("$.pacote.items[0].telefone").value("(31) 95678-1234"))
                        .andExpect(jsonPath("$.pacote.items[1].type").value("HOTEL"))
                        .andExpect(jsonPath("$.pacote.items[1].id").value("3435e609-a7fe-41fe-874b-058549001b82"))
                        .andExpect(jsonPath("$.pacote.items[1].preco").value(620.0))
                        .andExpect(jsonPath("$.pacote.items[1].nomeHotel").value("Pousada da Serra"))
                        .andExpect(jsonPath("$.pacote.items[1].endereco.id").value("740e50e9-75b5-484c-9141-757f8b416d14"))
                        .andExpect(jsonPath("$.pacote.items[1].endereco.cep").value("15001899"))
                        .andExpect(jsonPath("$.pacote.items[1].endereco.logradouro").value("Rua Ipiranga"))
                        .andExpect(jsonPath("$.pacote.items[1].endereco.numero").value("1604"))
                        .andExpect(jsonPath("$.pacote.items[1].endereco.complemento").value("Bloco B"))
                        .andExpect(jsonPath("$.pacote.items[1].endereco.bairro").value("Lapa"))
                        .andExpect(jsonPath("$.pacote.items[1].endereco.localidade.id").value("0359ed1a-cacb-47df-b04e-4c1c6c4f9bc3"))
                        .andExpect(jsonPath("$.pacote.items[1].endereco.localidade.nomeCidade").value("Rio de Janeiro"))
                        .andExpect(jsonPath("$.pacote.items[1].endereco.localidade.estado").value("RJ"))
                        .andExpect(jsonPath("$.pacote.items[1].endereco.localidade.codigoLocadoraVeiculo").value("D9462565"))
                        .andExpect(jsonPath("$.pacote.items[1].email").value("contato@pousadadaserra.com"))
                        .andExpect(jsonPath("$.pacote.items[1].telefone").value("(47) 98765-4321"))
                        .andExpect(jsonPath("$.pacote.items[1].idPlataforma").value(7920324))
                        .andExpect(jsonPath("$.pacote.items[2].type").value("TRANSLADO_AEREO"))
                        .andExpect(jsonPath("$.pacote.items[2].id").value("c76cf543-8be4-4ffc-9ae9-8b94acfef8c0"))
                        .andExpect(jsonPath("$.pacote.items[2].preco").value(320.0))
                        .andExpect(jsonPath("$.pacote.items[2].nomeCompanhia").value("Voe Rápido"))
                        .andExpect(jsonPath("$.pacote.items[2].vooIdaNumero").value("VR9876"))
                        .andExpect(jsonPath("$.pacote.items[2].vooVoltaNumero").value("VR4321"))
                        .andExpect(jsonPath("$.pacote.items[2].vooIdaHora").value("19:00:00"))
                        .andExpect(jsonPath("$.pacote.items[2].vooVoltaHora").value("21:30:00"));
            }

            @Test
            @DisplayName("Deve retornar erro 401 se não autenticado")
            void deveRetornarErro401SemToken() throws Exception {
                mockMvc.perform(get(BASE_URL + CLIENTE_ID + "/compras/" + COMPRA_ID)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isUnauthorized());
            }

            @Test
            @DisplayName("Deve retornar erro 400 se o id do cliente não for UUID válido")
            void deveRetornarErro400IdClienteInvalido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(get(BASE_URL + "kkk" + "/compras/" + COMPRA_ID)
                                .header("Authorization", "Bearer " + token)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("Deve retornar erro 400 se o id da compra não for UUID válido")
            void deveRetornarErro400IdCompraInvalido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(get(BASE_URL + CLIENTE_ID + "/compras/KKK")
                                .header("Authorization", "Bearer " + token)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("Deve retornar erro 404 se o id do cliente percenter ao outra compra")
            void deveRetornarErro404IdClienteInvalido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(get(BASE_URL + OUTRO_CLIENTE_ID + "/compras/" + COMPRA_ID)
                                .header("Authorization", "Bearer " + token)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }

            @Test
            @DisplayName("Deve retornar erro 404 se o id da compra percenter a outro cliente")
            void deveRetornarErro404IdCompraInvalido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(get(BASE_URL + CLIENTE_ID + "/compras/" + OUTRA_COMPRA_ID)
                                .header("Authorization", "Bearer " + token)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("Obter multiplas contratacoes")
        class PagedBasedTest {
            public static final int TOTAL_CONTRATACOES = 34; // Ajuste conforme o dataset real
            public static final String CLIENTE_ID = "ec2e748a-987d-4853-85d4-79ffaaf28b7b";

            @Test
            @DisplayName("Deve retornar lista de compras do cliente paginada e validar atributos básicos do primeiro item e header X-Total-Count")
            void deveListarComprasClienteComPaginacaoPadrao(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + CLIENTE_ID + "/compras")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.header().string("X-Total-Count", String.valueOf(TOTAL_CONTRATACOES)))
                        .andExpect(jsonPath("$[0].id").value("010d5341-f221-4fd3-b6af-6ee124d8dc5c"))
                        .andExpect(jsonPath("$[0].dataCompra").value("2026-01-17"))
                        .andExpect(jsonPath("$[0].dataIda").value("2026-05-10"))
                        .andExpect(jsonPath("$[0].dataVolta").value("2026-05-24"))
                        .andExpect(jsonPath("$[0].valorTotal").value(1248.1124))
                        .andExpect(jsonPath("$[0].desconto").value(0.12))
                        .andExpect(jsonPath("$[0].valorPago").value(1418.3096))
                        .andExpect(jsonPath("$[0].stripeChargeId").value("ch_bdf4ef56a6854da18e1f9fce"))
                        .andExpect(jsonPath("$[0].reservaHotel").value("RH702dbfe6"))
                        .andExpect(jsonPath("$[0].reservaVooIda").value("ETI7c141f7bc2"))
                        .andExpect(jsonPath("$[0].assentoVooIda").value("38-F"))
                        .andExpect(jsonPath("$[0].reservaVooVolta").value("ETV79721bb934"))
                        .andExpect(jsonPath("$[0].assentoVooVolta").value("4-D"))
                        .andExpect(jsonPath("$[0].reservaVeiculo").value("RVd66ea549"))
                        .andExpect(jsonPath("$[0].embarqueVooIda").value("2026-02-13T00:00:00"))
                        .andExpect(jsonPath("$[0].embarqueVooVolta").value("2026-05-27T00:00:00"))
                        .andExpect(jsonPath("$[0].cliente.id").value("ec2e748a-987d-4853-85d4-79ffaaf28b7b"))
                        .andExpect(jsonPath("$[0].cliente.nome").value("Lucas Lima"))
                        .andExpect(jsonPath("$[0].cliente.dataNascimento").value("1997-09-18"))
                        .andExpect(jsonPath("$[0].cliente.email").value("lucas.lima78@mail.com"))
                        .andExpect(jsonPath("$[0].cliente.telefone").value("(82) 99802-0917"))
                        .andExpect(jsonPath("$[0].cliente.endereco.id").value("1b80d79e-166d-4753-adb8-7615f2dc398e"))
                        .andExpect(jsonPath("$[0].cliente.endereco.cep").value("56334960"))
                        .andExpect(jsonPath("$[0].cliente.endereco.logradouro").value("Avenida Dom Pedro II"))
                        .andExpect(jsonPath("$[0].cliente.endereco.numero").value("566"))
                        .andExpect(jsonPath("$[0].cliente.endereco.complemento").value("Fundos"))
                        .andExpect(jsonPath("$[0].cliente.endereco.bairro").value("Santa Cecília"))
                        .andExpect(jsonPath("$[0].cliente.endereco.localidade.id").value("0c6c715c-8c82-407d-8fc8-5d4fab0c8284"))
                        .andExpect(jsonPath("$[0].cliente.endereco.localidade.nomeCidade").value("Porto Alegre"))
                        .andExpect(jsonPath("$[0].cliente.endereco.localidade.estado").value("RS"))
                        .andExpect(jsonPath("$[0].cliente.endereco.localidade.codigoLocadoraVeiculo").value("C4668F0F"))
                        .andExpect(jsonPath("$[0].pacote.id").value("701d2fea-09c2-4a92-ab78-3e36b423e53c"))
                        .andExpect(jsonPath("$[0].pacote.nome").value("Viagem de Trem em Curitiba"))
                        .andExpect(jsonPath("$[0].pacote.localidade.id").value("933243f9-4aec-4288-af69-4d6ecf0cc16b"))
                        .andExpect(jsonPath("$[0].pacote.localidade.nomeCidade").value("Curitiba"))
                        .andExpect(jsonPath("$[0].pacote.localidade.estado").value("PR"))
                        .andExpect(jsonPath("$[0].pacote.localidade.codigoLocadoraVeiculo").value("FEF47CEE"))
                        .andExpect(jsonPath("$[0].pacote.dataInicio").value("2026-04-01"))
                        .andExpect(jsonPath("$[0].pacote.dataFim").value("2226-04-08"))
                        .andExpect(jsonPath("$[0].pacote.disponibilidade").value(2))
                        .andExpect(jsonPath("$[0].pacote.desconto").value(0.3))
                        .andExpect(jsonPath("$[0].pacote.duracao").value(7))
                        .andExpect(jsonPath("$[0].pacote.tipoDesconto").value("FIXO"))
                        .andExpect(jsonPath("$[0].pacote.valorDesconto").value(327.0))
                        .andExpect(jsonPath("$[0].pacote.preco").value(1090.0))
                        .andExpect(jsonPath("$[0].pacote.custo").value(763.0))
                        .andExpect(jsonPath("$[0].pacote.items[0].type").value("ALUGUEL_VEICULO"))
                        .andExpect(jsonPath("$[0].pacote.items[0].id").value("0f00d712-afe9-4af2-a33d-e2c11dc97217"))
                        .andExpect(jsonPath("$[0].pacote.items[0].preco").value(150.0))
                        .andExpect(jsonPath("$[0].pacote.items[0].nomeLocadora").value("Quilômetro Certo"))
                        .andExpect(jsonPath("$[0].pacote.items[0].categoriaCarro").value("SUV"))
                        .andExpect(jsonPath("$[0].pacote.items[0].email").value("atendimento@quilometrocerto.com.br"))
                        .andExpect(jsonPath("$[0].pacote.items[0].telefone").value("(31) 95678-1234"))
                        .andExpect(jsonPath("$[0].pacote.items[1].type").value("HOTEL"))
                        .andExpect(jsonPath("$[0].pacote.items[1].id").value("3435e609-a7fe-41fe-874b-058549001b82"))
                        .andExpect(jsonPath("$[0].pacote.items[1].preco").value(620.0))
                        .andExpect(jsonPath("$[0].pacote.items[1].nomeHotel").value("Pousada da Serra"))
                        .andExpect(jsonPath("$[0].pacote.items[1].endereco.id").value("740e50e9-75b5-484c-9141-757f8b416d14"))
                        .andExpect(jsonPath("$[0].pacote.items[1].endereco.cep").value("15001899"))
                        .andExpect(jsonPath("$[0].pacote.items[1].endereco.logradouro").value("Rua Ipiranga"))
                        .andExpect(jsonPath("$[0].pacote.items[1].endereco.numero").value("1604"))
                        .andExpect(jsonPath("$[0].pacote.items[1].endereco.complemento").value("Bloco B"))
                        .andExpect(jsonPath("$[0].pacote.items[1].endereco.bairro").value("Lapa"))
                        .andExpect(jsonPath("$[0].pacote.items[1].endereco.localidade.id").value("0359ed1a-cacb-47df-b04e-4c1c6c4f9bc3"))
                        .andExpect(jsonPath("$[0].pacote.items[1].endereco.localidade.nomeCidade").value("Rio de Janeiro"))
                        .andExpect(jsonPath("$[0].pacote.items[1].endereco.localidade.estado").value("RJ"))
                        .andExpect(jsonPath("$[0].pacote.items[1].endereco.localidade.codigoLocadoraVeiculo").value("D9462565"))
                        .andExpect(jsonPath("$[0].pacote.items[1].email").value("contato@pousadadaserra.com"))
                        .andExpect(jsonPath("$[0].pacote.items[1].telefone").value("(47) 98765-4321"))
                        .andExpect(jsonPath("$[0].pacote.items[1].idPlataforma").value(7920324))
                        .andExpect(jsonPath("$[0].pacote.items[2].type").value("TRANSLADO_AEREO"))
                        .andExpect(jsonPath("$[0].pacote.items[2].id").value("c76cf543-8be4-4ffc-9ae9-8b94acfef8c0"))
                        .andExpect(jsonPath("$[0].pacote.items[2].preco").value(320.0))
                        .andExpect(jsonPath("$[0].pacote.items[2].nomeCompanhia").value("Voe Rápido"))
                        .andExpect(jsonPath("$[0].pacote.items[2].vooIdaNumero").value("VR9876"))
                        .andExpect(jsonPath("$[0].pacote.items[2].vooVoltaNumero").value("VR4321"))
                        .andExpect(jsonPath("$[0].pacote.items[2].vooIdaHora").value("19:00:00"))
                        .andExpect(jsonPath("$[0].pacote.items[2].vooVoltaHora").value("21:30:00"));
            }

            @Test
            @DisplayName("Deve retornar lista de compras do cliente com paginação customizada e validar quantidade de elementos e header X-Total-Count")
            void deveListarComprasClienteComPaginacaoCustomizada(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
                int page = 1;
                int size = 3;
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + CLIENTE_ID + "/compras?page=" + page + "&size=" + size)
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
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + CLIENTE_ID + "/compras?page=" + page + "&size=" + size)
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
                mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + CLIENTE_ID + "/compras?page=-1&size=abc")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("Deve retornar erro 401 se não autenticado")
            void deveRetornarErro401SemToken() throws Exception {
                mockMvc.perform(get(BASE_URL + CLIENTE_ID + "/compras")
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    @DisplayName("Obter multiplos clientes")
    class PagedBasedTest {
        @Test
        @DisplayName("Deve retornar a lista de clientes paginada e validar todos os atributos do primeiro cliente e o header X-Total-Count")
        void deveListarClientesComPaginacaoPadrao(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/v1/clientes")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.header().string("X-Total-Count", String.valueOf(TOTAL_ITEMS)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(20)))
                    .andExpect(jsonPath("$[0].id").value("002b1b99-1cd6-4d02-854b-3d487a79d436"))
                    .andExpect(jsonPath("$[0].nome").value("Pedro Nunes"))
                    .andExpect(jsonPath("$[0].dataNascimento").value("2002-12-31"))
                    .andExpect(jsonPath("$[0].email").value("pedro.nunes33@site.com"))
                    .andExpect(jsonPath("$[0].telefone").value("(21) 99653-1754"))
                    .andExpect(jsonPath("$[0].endereco.id").value("2b333f57-788a-4724-a7a3-b017d190832b"))
                    .andExpect(jsonPath("$[0].endereco.cep").value("57432935"))
                    .andExpect(jsonPath("$[0].endereco.logradouro").value("Travessa Brasil"))
                    .andExpect(jsonPath("$[0].endereco.numero").value("80"))
                    .andExpect(jsonPath("$[0].endereco.complemento").value("Sobreloja"))
                    .andExpect(jsonPath("$[0].endereco.bairro").value("Boa Vista"))
                    .andExpect(jsonPath("$[0].endereco.localidade.id").value("3f725fe9-ba1f-4f8f-8542-1582e80c3724"))
                    .andExpect(jsonPath("$[0].endereco.localidade.nomeCidade").value("Brasília"))
                    .andExpect(jsonPath("$[0].endereco.localidade.estado").value("DF"))
                    .andExpect(jsonPath("$[0].endereco.localidade.codigoLocadoraVeiculo").value("D8561091"));
        }

        @Test
        @DisplayName("Deve retornar a lista de clientes com paginação customizada e validar quantidade de elementos e header X-Total-Count")
        void deveListarClientesComPaginacaoCustomizada(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            int page = 1;
            int size = 3;
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/clientes?page=" + page + "&size=" + size)
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
        @DisplayName("Deve retornar página vazia ao solicitar página fora do range")
        void deveRetornarPaginaVaziaForaDoRange(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            int page = 100;
            int size = 5;
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/clientes?page=" + page + "&size=" + size)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.header().string("X-Total-Count", String.valueOf(TOTAL_ITEMS)))
                    .andReturn();
            String content = result.getResponse().getContentAsString();
            JsonNode root = objectMapper.readTree(content);
            assertThat(root.isArray()).isTrue();
            assertThat(root.size()).isEqualTo(0);
        }

        @Test
        @DisplayName("Deve retornar 200 ao passar parâmetros de paginação inválidos (comportamento padrão do Spring)")
        void deveRetornar200PaginacaoInvalida(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/v1/clientes?page=-1&size=abc")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Deve retornar erro 401 se não autenticado")
        void deveRetornarErro401SemToken() throws Exception {
            mockMvc.perform(get(BASE_URL)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("Obter um cliente")
    class SingleEntityTest {

        @Test
        @DisplayName("Deve retornar erro 400 se o id não for UUID válido")
        void deveRetornarErro400IdInvalido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            mockMvc.perform(get(BASE_URL + "/abc")
                            .header("Authorization", "Bearer " + token)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Deve retornar erro 401 se não autenticado")
        void deveRetornarErro401SemToken() throws Exception {
            String localidadeId = "00000000-0000-0000-0000-000000000001";
            mockMvc.perform(get(BASE_URL + "/" + localidadeId)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("Deve retornar todos os atributos do cliente ao buscar por ID existente")
        void deveObterClientePorIdExistente(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            String idExistente = "ec2e748a-987d-4853-85d4-79ffaaf28b7b";
            mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + idExistente)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(idExistente))
                    .andExpect(jsonPath("$.nome").value("Lucas Lima"))
                    .andExpect(jsonPath("$.dataNascimento").value("1997-09-18"))
                    .andExpect(jsonPath("$.email").value("lucas.lima78@mail.com"))
                    .andExpect(jsonPath("$.telefone").value("(82) 99802-0917"))
                    .andExpect(jsonPath("$.endereco.id").value("1b80d79e-166d-4753-adb8-7615f2dc398e"))
                    .andExpect(jsonPath("$.endereco.cep").value("56334960"))
                    .andExpect(jsonPath("$.endereco.logradouro").value("Avenida Dom Pedro II"))
                    .andExpect(jsonPath("$.endereco.numero").value("566"))
                    .andExpect(jsonPath("$.endereco.complemento").value("Fundos"))
                    .andExpect(jsonPath("$.endereco.bairro").value("Santa Cecília"))
                    .andExpect(jsonPath("$.endereco.localidade.id").value("0c6c715c-8c82-407d-8fc8-5d4fab0c8284"))
                    .andExpect(jsonPath("$.endereco.localidade.nomeCidade").value("Porto Alegre"))
                    .andExpect(jsonPath("$.endereco.localidade.estado").value("RS"))
                    .andExpect(jsonPath("$.endereco.localidade.codigoLocadoraVeiculo").value("C4668F0F"));
        }

        @Test
        @DisplayName("Deve retornar 404 ao buscar cliente inexistente")
        void deveRetornar404ClienteInexistente(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            String idInexistente = "00000000-0000-0000-0000-000000009999";
            mockMvc.perform(MockMvcRequestBuilders.get("/v1/clientes/" + idInexistente)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                    .andExpect(status().isNotFound());
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
