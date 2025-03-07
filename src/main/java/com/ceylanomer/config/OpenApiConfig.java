package com.ceylanomer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Java DDD CQRS POC API")
                        .description("API Documentation for Java DDD CQRS POC Application")
                        .version("v1.0.0"));
    }
} 