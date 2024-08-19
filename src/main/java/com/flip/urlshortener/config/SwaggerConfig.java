package com.flip.urlshortener.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String API_TITLE = "Flip - URL Shortener Service";
    private static final String API_DESCRIPTION = "A service to shorten URLs and retrieve original URLs. Provides endpoints for creating short URLs and resolving them back to the original long URLs.";
    private static final String API_VERSION = "1.0";
    private static final String CONTACT_NAME = "Özlem Engez";
    private static final String CONTACT_EMAIL = "engezozlem8@gmail.com";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .contact(apiContact());
    }

    private Contact apiContact() {
        return new Contact()
                .name(CONTACT_NAME)
                .email(CONTACT_EMAIL);
    }
}
