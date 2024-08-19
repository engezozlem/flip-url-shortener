package com.flip.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when there is an issue with the domain name in a URL.
 * This exception results in an HTTP 400 Bad Request response.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DomainNameException extends RuntimeException {
    public DomainNameException(String message) {
        super(message);
    }
}
