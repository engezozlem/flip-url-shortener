package com.flip.urlshortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * SwaggerRedirectController handles the redirection from the root URL ("/")
 * to the Swagger UI page.
 */
@Controller
public class SwaggerRedirectController {

    /**
     * Redirects the root URL ("/") to the Swagger UI page.
     *
     * @return a redirect string to the Swagger UI URL
     */
    @GetMapping("/")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui.html";
    }
}
