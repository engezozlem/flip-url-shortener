package com.flip.urlshortener.service;

import com.flip.urlshortener.exception.UrlNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class UrlShortenerServiceImplTest {

    private UrlShortenerService urlShortenerService;

    @BeforeEach
    void setUp() {
        urlShortenerService = new UrlShortenerServiceImpl();
    }

    @Test
    void testShortenUrl() {
        String longUrl = "https://open.spotify.com/track/0ofHAoxe9vBkTCp2UQIavz?si=5ee5c3cba54b4697";
        String shortUrl = urlShortenerService.shortenUrl(longUrl);

        assertNotNull(shortUrl);
        assertEquals(longUrl, urlShortenerService.getOriginalUrl(shortUrl));
    }

    @Test
    void testGetOriginalUrl() {
        String longUrl = "https://open.spotify.com/track/1k1Bqnv2R0uJXQN4u6LKYt?si=50c0ff5ab85c4a9c";
        String shortUrl = urlShortenerService.shortenUrl(longUrl);

        String originalUrl = urlShortenerService.getOriginalUrl(shortUrl);
        assertEquals(longUrl, originalUrl);
    }

    @Test
    void testGetOriginalUrlNotFound() {
        String shortUrl = "nonExistentUrl";

        UrlNotFoundException exception = assertThrows(UrlNotFoundException.class, () -> urlShortenerService.getOriginalUrl(shortUrl));

        assertEquals("URL not found", exception.getMessage());
    }

    @Test
    void testShortenUrl_Idempotency() {
        String longUrl = "https://open.spotify.com/track/2WmbbiWfFEKsSZe6E5GeVe?si=702aa0b8d9a745e8";
        String firstShortUrl = urlShortenerService.shortenUrl(longUrl);
        String secondShortUrl = urlShortenerService.shortenUrl(longUrl);

        assertEquals(firstShortUrl, secondShortUrl);
    }
}
