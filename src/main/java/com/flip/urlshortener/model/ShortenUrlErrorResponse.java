package com.flip.urlshortener.model;

import java.util.Map;

/**
 * Represents an error response for URL shortening operations.
 * Contains a map of error messages keyed by field names.
 */
public class ShortenUrlErrorResponse {
    private Map<String, String> errors;

    public ShortenUrlErrorResponse(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
