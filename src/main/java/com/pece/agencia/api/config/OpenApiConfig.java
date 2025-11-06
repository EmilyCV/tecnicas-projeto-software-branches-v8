package com.pece.agencia.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Agência de Viagens")
                        .version("1.0.0")
                        .description("Documentação interativa da API da Agência de Viagens usando Springdoc OpenAPI."));
    }
}

