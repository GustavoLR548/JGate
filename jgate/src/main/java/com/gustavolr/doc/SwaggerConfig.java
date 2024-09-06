package com.gustavolr.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("JGate - Rest API")
                .version("1.0")
                .description("API de autenticação")
                .contact(new Contact()
                    .name("GustavoLR548")
                    .url("https://github.com/GustavoLR548")
                    .email("gustavolr035@gmail.com")
                )
            );
    }
}