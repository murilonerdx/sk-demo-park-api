package com.github.murilonerdx.skdemoparkapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(
                        new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes("security_auth", securityConfig()))
                .info(
                        new Info()
                                .title("REST API - Spring Park")
                                .description("API para gestão de estacionamento de veículos")
                                .version("v1")
                                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                                .contact(new Contact().name("Murilo P.S").email("mu-silva@outlook.com"))
                );
    }

    @Bean
    SecurityScheme securityConfig(){
        return new SecurityScheme()
                .description("Informar o token JWT")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .name("security_auth")
                .bearerFormat("JWT")
                .scheme("bearer");
    }

}
