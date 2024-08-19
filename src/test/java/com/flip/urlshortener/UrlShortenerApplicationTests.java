package com.flip.urlshortener;

import com.flip.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UrlShortenerApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Test
    void contextLoads() {
        assertThat(Boolean.TRUE).isTrue();
    }

    @Test
    void testUrlShortenerServiceBeanIsLoaded() {
        assertNotNull(urlShortenerService, "UrlShortenerService bean should be loaded in the context");
    }

    @Test
    void testApplicationContext() {
        assertNotNull(applicationContext, "Application context should have loaded");
    }
}
