package com.lavanderia.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .version("1.0")
                        .description("API para la gestión de usuarios, tenants y roles en el sistema de lavanderías")
                        .contact(new Contact()
                                .name("SaaS Lavanderías")
                                .email("soporte@lavanderias.com")))
                // 🔹 Usa URL relativa para que Swagger detecte automáticamente el dominio y HTTPS
                .addServersItem(new Server().url("/"));
    }
}
