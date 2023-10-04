package com.chatop.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OpenAPIConfiguration {

    private static final String SECURITY_SCHEME_NAME = "openapi";
    
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
        .components(
            new Components().addSecuritySchemes(
                SECURITY_SCHEME_NAME, 
                new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .description("openapi")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
            )
        )
        .addSecurityItem(
            new SecurityRequirement().addList(SECURITY_SCHEME_NAME)
        )
        .info(
            new Info()
                .title("Api")
                .description("")
                .version("1.0")
        );
    }
}


