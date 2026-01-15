package com.lavanderia.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(HttpServletRequest request) {
        String scheme = request.getScheme(); // http o https
        String host = request.getHeader("host"); // dominio dinámico
        String url = scheme + "://" + host;

        return new OpenAPI()
                .addServersItem(new Server().url(url))
                .info(new Info()
                        .title("User Service API")
                        .version("1.0"));
    }
}