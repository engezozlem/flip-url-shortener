package com.flip.urlshortener.service;

/**
 * Service interface for URL shortening operations.
 * Provides methods to shorten a long URL and retrieve the original long URL from a shortened URL.
 */
public interface UrlShortenerService {

    /**
     * Shortens a given long URL.
     *
     * @param longUrl the original long URL to be shortened
     * @return the shortened URL
     */
    String shortenUrl(String longUrl);

    /**
     * Retrieves the original long URL for a given shortened URL.
     *
     * @param shortUrl the shortened URL
     * @return the original long URL, or null if the shortened URL does not exist
     */
    String getOriginalUrl(String shortUrl);
}
