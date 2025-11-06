package com.pece.agencia.api.controller;

import com.pece.agencia.api.AbstractTest;
import com.pece.agencia.api.utils.TokenFor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class LocalidadeControllerTest extends AbstractTest {

    private static final String BASE_URL = "/v1/localidades";

    @Nested
    @DisplayName("Obter multiplas localidades")
    class PagedBasedTest {
        @Test
        @DisplayName("Deve retornar lista de localidades com status 200 e header X-Total-Count")
        void deveRetornarListaDeLocalidades(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            mockMvc.perform(get(BASE_URL)
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(header().string("X-Total-Count", "5570"))
                    // verificar que o payload tem o tamanho da página padrão (20)
                    // verificar todos os atributos da primeira entidade retornada e o tamanho da página
                    .andExpect(jsonPath("$", hasSize(20)))
                    .andExpect(jsonPath("$[0].id", is("000a94d4-3272-4f01-bf38-65bdd10ed9df")))
                    .andExpect(jsonPath("$[0].nomeCidade", equalToIgnoringWhiteSpace("Wenceslau Guimarães")))
                    .andExpect(jsonPath("$[0].estado", is("BA")))
                    .andExpect(jsonPath("$[0].codigoLocadoraVeiculo", is("E5134985")));
        }

        @Test
        @DisplayName("Deve retornar lista paginada corretamente")
        void deveRetornarListaPaginada(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            mockMvc.perform(get(BASE_URL)
                    .param("page", "0")
                    .param("size", "2")
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(header().string("X-Total-Count", "5570"))
                    .andExpect(jsonPath("$", hasSize(2)));
        }

        @Test
        @DisplayName("Deve retornar página cheia se offset fora do range (comportamento atual)")
        void deveRetornarPaginaCheiaForaDoRange(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            mockMvc.perform(get(BASE_URL)
                    .param("page", "100")
                    .param("size", "10")
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(10)));
        }

        @Test
        @DisplayName("Deve aceitar parâmetros de paginação negativos (comportamento atual)")
        void deveAceitarPaginacaoInvalida(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            mockMvc.perform(get(BASE_URL)
                    .param("page", "-1")
                    .param("size", "-5")
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON))
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
    @DisplayName("Obter uma localidade")
    class SingleEntityTest {
        @Test
        @DisplayName("Deve retornar localidade existente pelo id com status 200 e todos os atributos")
        void deveRetornarLocalidadePorId(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            // UUID real de localidade existente
            String localidadeId = "000a94d4-3272-4f01-bf38-65bdd10ed9df";
            mockMvc.perform(get(BASE_URL + "/" + localidadeId)
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(localidadeId)))
                    .andExpect(jsonPath("$.nomeCidade", is("Wenceslau Guimarães")))
                    .andExpect(jsonPath("$.estado", is("BA")))
                    .andExpect(jsonPath("$.codigoLocadoraVeiculo", is("E5134985")));
        }

        @Test
        @DisplayName("Deve retornar erro 404 se a localidade não existir")
        void deveRetornarErro404SeNaoExistir(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            String localidadeId = UUID.randomUUID().toString();
            mockMvc.perform(get(BASE_URL + "/" + localidadeId)
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
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
        @DisplayName("Deve retornar erro 400 se o id não for UUID válido")
        void deveRetornarErro400IdInvalido(@TokenFor(username = "camila.mendes78", password = "x") String token) throws Exception {
            mockMvc.perform(get(BASE_URL + "/abc")
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON))
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
