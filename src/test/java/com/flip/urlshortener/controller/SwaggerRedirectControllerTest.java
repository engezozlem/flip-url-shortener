package com.flip.urlshortener.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class SwaggerRedirectControllerTest {

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new SwaggerRedirectController()).build();

    @Test
    void testRedirectToSwagger() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/swagger-ui.html"));
    }
}
