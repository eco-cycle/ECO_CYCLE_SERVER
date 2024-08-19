package com.hackathon.ecocycle.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Value("${myapp.api-url}")
    private String prodUrl;

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("ECO CYCLE API Document")
                .version("v0.0.1")
                .description("ECO CYCLE API 문서");

        String authName = "Json Web Token";

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(authName);
        Components components = new Components()
                .addSecuritySchemes(
                        authName,
                        new SecurityScheme()
                                .name(authName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT")
                                .description("Access Token 토큰을 입력해주세요.(Bearer X)")
                );

        Server prodServer = new Server();
        prodServer.description("Production Server")
                .url(prodUrl);

        Server localServer = new Server();
        localServer.description("Development Server")
                .url("http://localhost:8092");

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components)
                .info(info)
                .servers(Arrays.asList(prodServer, localServer));
    }

}