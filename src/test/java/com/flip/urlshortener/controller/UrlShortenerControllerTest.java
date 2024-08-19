package com.flip.urlshortener.controller;

import com.flip.urlshortener.dto.UrlDTO;
import com.flip.urlshortener.exception.UrlNotFoundException;
import com.flip.urlshortener.model.LongUrlResponse;
import com.flip.urlshortener.model.ShortenUrlErrorResponse;
import com.flip.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UrlShortenerControllerTest {

    @InjectMocks
    private UrlShortenerController urlShortenerController;

    @Mock
    private UrlShortenerService urlShortenerService;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShortenUrlSuccess() {
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setLongUrl("https://open.spotify.com/track/2DpJ9T2RVRanZcYFHKOAfA?si=207b91af1a6e4b25");
        String shortUrl = "https://open.spotify.com/ph3OIlQk";

        when(urlShortenerService.shortenUrl(urlDTO.getLongUrl())).thenReturn(shortUrl);

        ResponseEntity<?> response = urlShortenerController.shortenUrl(urlDTO, bindingResult);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    public void testShortenUrlValidationError() {
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setLongUrl("invalid_url");
        FieldError fieldError = new FieldError("urlDTO", "longUrl", "Long URL must start with http:// or https://");

        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(java.util.Collections.singletonList(fieldError));

        ResponseEntity<?> response = urlShortenerController.shortenUrl(urlDTO, bindingResult);

        assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
        assertInstanceOf(ShortenUrlErrorResponse.class, response.getBody());
    }

    @Test
    public void testGetOriginalUrlFound() {
        String shortUrl = "https://open.spotify.com/psEAMwT6";
        String longUrl = "https://open.spotify.com/track/2DpJ9T2RVRanZcYFHKOAfA?si=207b91af1a6e4b25";

        when(urlShortenerService.getOriginalUrl(shortUrl)).thenReturn(longUrl);

        ResponseEntity<?> response = urlShortenerController.getOriginalUrl(shortUrl);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertInstanceOf(LongUrlResponse.class, response.getBody());
    }

    @Test
    public void testGetOriginalUrlNotFound() {
        String shortUrl = "psEAMwT6";

        when(urlShortenerService.getOriginalUrl(shortUrl)).thenThrow(new UrlNotFoundException("URL not found"));

        UrlNotFoundException thrown = assertThrowsExactly(UrlNotFoundException.class, () -> {
            urlShortenerController.getOriginalUrl(shortUrl);
        });

        assertEquals("URL not found", thrown.getMessage());
    }

    @Test
    public void testGetOriginalUrlNotFoundForNullUrl() {
        String shortUrl = "psEAMwT6";

        when(urlShortenerService.getOriginalUrl(shortUrl)).thenReturn(null);

        // Act
        ResponseEntity<?> response = urlShortenerController.getOriginalUrl(shortUrl);

        // Assert
        assertEquals(ResponseEntity.notFound().build(), response);
    }
}
