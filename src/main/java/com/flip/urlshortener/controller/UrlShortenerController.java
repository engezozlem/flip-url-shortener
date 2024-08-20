package com.flip.urlshortener.controller;

import com.flip.urlshortener.dto.UrlDTO;
import com.flip.urlshortener.model.LongUrlResponse;
import com.flip.urlshortener.model.ShortenUrlErrorResponse;
import com.flip.urlshortener.model.ShortenUrlResponse;
import com.flip.urlshortener.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * UrlShortenerController provides REST endpoints for shortening URLs
 * and retrieving the original long URLs.
 */
@RestController
@RequestMapping("/api/v1/urls")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    /**
     * Constructor for UrlShortenerController.
     *
     * @param urlShortenerService the service for URL shortening operations
     */
    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    /**
     * Shortens a given long URL.
     *
     * @param urlDTO the data transfer object containing the long URL
     * @param result the binding result for validation errors
     * @return a ResponseEntity containing the shortened URL or validation errors
     */
    @PostMapping("/shorten")
    @Operation(
            summary = "Shorten a URL",
            description = "Receives a long URL and returns a shortened URL.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UrlDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Shortened URL",
                            content = @Content(
                                    schema = @Schema(implementation = ShortenUrlResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation errors",
                            content = @Content(
                                    schema = @Schema(implementation = ShortenUrlErrorResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> shortenUrl(@Valid @RequestBody UrlDTO urlDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach(error ->
                    errors.put(((FieldError) error).getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(new ShortenUrlErrorResponse(errors));
        }

        String shortUrl = urlShortenerService.shortenUrl(urlDTO.getLongUrl());
        return ResponseEntity.ok(new ShortenUrlResponse(shortUrl));
    }

    /**
     * Retrieves the original long URL for a given shortened URL.
     *
     * @param shortUrl the shortened URL
     * @return a ResponseEntity containing the original long URL or a 404 status if not found
     */
    @GetMapping
    @Operation(
            summary = "Retrieve original URL",
            description = "Receives a shortened URL and returns the original long URL.",
            responses = {
                    @ApiResponse(
                            description = "Original long URL",
                            content = @Content(
                                    schema = @Schema(type = "string")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "URL not found"
                    )
            }
    )
    public ResponseEntity<?> getOriginalUrl(@RequestParam String shortUrl) {
        String longUrl = urlShortenerService.getOriginalUrl(shortUrl);
        if (StringUtils.isEmpty(longUrl)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new LongUrlResponse(longUrl));
    }
}
