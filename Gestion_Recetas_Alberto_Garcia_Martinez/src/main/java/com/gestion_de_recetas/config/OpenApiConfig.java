package com.gestion_de_recetas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("API de Gestión de Recetas")
                .version("1.0.0")
                .description("API REST para gestionar categorías, recetas e ingredientes, "
                        + "incluida la relación muchos a muchos entre recetas e ingredientes."));
    }
}