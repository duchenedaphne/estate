package com.chatop.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OpenAPIConfiguration {

    private static final String SECURITY_SCHEME_NAME = "spring_oauth";
    
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
        .components(
            new Components().addSecuritySchemes(
                SECURITY_SCHEME_NAME, 
                new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .description("Google OAuth2")
                .type(SecurityScheme.Type.OAUTH2)
                .scheme("google-oauth2")
                .bearerFormat("JWT")
                .flows(
                    new OAuthFlows().authorizationCode(
                        new OAuthFlow()
                        .authorizationUrl(
                            "https://accounts.google.com/o/oauth2/v2/auth"
                        )
                        .tokenUrl(
                            "https://www.googleapis.com/oauth2/v4/token"
                        )
                        // .refreshUrl("https://www.googleapis.com/oauth2/v4/token")
                        // .userNameAttributeName(IdTokenClaimNames.SUB)
                        // .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                        // .scope("openid", "profile", "email", "address", "phone")
                        .scopes(
                            new Scopes()
                            .addString("openid", "openid")
                            .addString("email", "https://www.googleapis.com/oauth2/v3/userinfo.email")
                            .addString("profile","https://www.googleapis.com/oauth2/v3/userinfo.profile")
                        )
                    )
                )
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


