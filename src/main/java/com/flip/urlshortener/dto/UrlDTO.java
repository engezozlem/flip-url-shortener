package com.flip.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object (DTO) for transferring URL data in requests.
 * Contains validation annotations to ensure the long URL is not blank and follows a proper format.
 */
public class UrlDTO {

    @NotBlank(message = "Long URL must not be blank")
    @Pattern(regexp = "^(http://|https://).+", message = "Long URL must start with http:// or https://")
    private String longUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
