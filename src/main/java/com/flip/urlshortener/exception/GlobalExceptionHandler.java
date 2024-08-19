package com.flip.urlshortener.exception;

import com.flip.urlshortener.model.ShortenUrlErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler handles exceptions thrown by the application
 * and returns appropriate HTTP responses with error details.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles generic exceptions that are not specifically handled by other methods.
     *
     * @param ex the thrown exception
     * @return a ResponseEntity containing a ShortenUrlErrorResponse and an HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ShortenUrlErrorResponse> handleGlobalException(Exception ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles UrlNotFoundException, thrown when a shortened URL cannot be found.
     *
     * @param ex the thrown UrlNotFoundException
     * @return a ResponseEntity containing a ShortenUrlErrorResponse and an HTTP 404 status
     */
    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ShortenUrlErrorResponse> handleUrlNotFoundException(UrlNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles DomainNameException, thrown when there is an issue with the domain name in the URL.
     *
     * @param ex the thrown DomainNameException
     * @return a ResponseEntity containing a ShortenUrlErrorResponse and an HTTP 400 status
     */
    @ExceptionHandler(DomainNameException.class)
    public ResponseEntity<ShortenUrlErrorResponse> handleDomainNameException(DomainNameException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles SchemaNameException, thrown when there is an issue with the schema in the URL.
     *
     * @param ex the thrown SchemaNameException
     * @return a ResponseEntity containing a ShortenUrlErrorResponse and an HTTP 400 status
     */
    @ExceptionHandler(SchemaNameException.class)
    public ResponseEntity<ShortenUrlErrorResponse> handleSchemaNameException(SchemaNameException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * A utility method to build a ResponseEntity with an error message and status code.
     *
     * @param errorMessage the error message to include in the response
     * @param status       the HTTP status code to return
     * @return a ResponseEntity containing a ShortenUrlErrorResponse and the provided status
     */
    private ResponseEntity<ShortenUrlErrorResponse> buildErrorResponse(String errorMessage, HttpStatus status) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", errorMessage);

        ShortenUrlErrorResponse shortenUrlErrorResponse = new ShortenUrlErrorResponse(errors);
        return new ResponseEntity<>(shortenUrlErrorResponse, status);
    }
}
