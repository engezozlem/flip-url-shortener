package com.flip.urlshortener.exception;

import com.flip.urlshortener.model.ShortenUrlErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleGlobalException() {
        Exception exception = new RuntimeException("An error occurred");

        ResponseEntity<ShortenUrlErrorResponse> response = globalExceptionHandler.handleGlobalException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ShortenUrlErrorResponse body = response.getBody();
        assertNotNull(body);
        Map<String, String> errors = body.getErrors();
        assertEquals("An error occurred", errors.get("error"));
    }

    @Test
    public void testHandleUrlNotFoundException() {
        UrlNotFoundException exception = new UrlNotFoundException("URL not found");

        ResponseEntity<ShortenUrlErrorResponse> response = globalExceptionHandler.handleUrlNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ShortenUrlErrorResponse body = response.getBody();
        assertNotNull(body);
        Map<String, String> errors = body.getErrors();
        assertEquals("URL not found", errors.get("error"));
    }

    @Test
    public void testHandleDomainNameException() {
        DomainNameException exception = new DomainNameException("Incorrect URL domain name");

        ResponseEntity<ShortenUrlErrorResponse> response = globalExceptionHandler.handleDomainNameException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ShortenUrlErrorResponse body = response.getBody();
        assertNotNull(body);
        Map<String, String> errors = body.getErrors();
        assertEquals("Incorrect URL domain name", errors.get("error"));
    }

    @Test
    public void testHandleSchemaNameException() {
        SchemaNameException exception = new SchemaNameException("Incorrect URL schema");

        ResponseEntity<ShortenUrlErrorResponse> response = globalExceptionHandler.handleSchemaNameException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ShortenUrlErrorResponse body = response.getBody();
        assertNotNull(body);
        Map<String, String> errors = body.getErrors();
        assertEquals("Incorrect URL schema", errors.get("error"));
    }
}
