package com.flip.urlshortener.model;

/**
 * A response model that contains the shortened URL.
 *
 * @param shortUrl the shortened URL
 */
public record ShortenUrlResponse(String shortUrl) {
}
