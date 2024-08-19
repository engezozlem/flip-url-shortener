package com.flip.urlshortener.model;

/**
 * A response model that contains the original long URL.
 *
 * @param longUrl the original long URL
 */
public record LongUrlResponse(String longUrl) {
}
