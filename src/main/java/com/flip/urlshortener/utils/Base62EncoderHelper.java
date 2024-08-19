package com.flip.urlshortener.utils;

import com.flip.urlshortener.exception.DomainNameException;
import com.flip.urlshortener.exception.SchemaNameException;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;

public class Base62EncoderHelper {
    private static final String CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = CHAR_SET.length();

    /**
     * Encodes a given value into a Base62 string.
     *
     * @param value the value to encode
     * @return the Base62 encoded string
     */
    public static String encode(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be greater than zero.");
        }

        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.append(CHAR_SET.charAt((int) (value % BASE)));
            value /= BASE;
        }
        return sb.reverse().toString();
    }

    /**
     * Extracts the domain name from the given URL.
     *
     * @param url the URL string
     * @return the domain name
     * @throws DomainNameException if the URL is invalid or domain name cannot be extracted
     */
    public static String getDomainName(String url) {
        URI uri = parseUri(url);
        String domain = uri.getHost();

        if (StringUtils.isBlank(domain)) {
            throw new DomainNameException("Incorrect domain name for URL: " + url);
        }

        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    /**
     * Extracts the schema from the given URL.
     *
     * @param url the URL string
     * @return the schema name
     * @throws SchemaNameException if the URL is invalid or schema name cannot be extracted
     */
    public static String getSchema(String url) {
        URI uri = parseUri(url);
        String scheme = uri.getScheme();

        if (StringUtils.isBlank(scheme)) {
            throw new SchemaNameException("Incorrect schema name for URL: " + url);
        }

        return scheme;
    }

    /**
     * Creates a shortened URL using the provided schema, domain, and short URL path.
     *
     * @param schemaName the schema name (e.g., http, https)
     * @param domainName the domain name (e.g., example.com)
     * @param shortUrl   the shortened URL path
     * @return the complete shortened URL
     */
    public static String createShortUrl(String schemaName, String domainName, String shortUrl) {
        if (StringUtils.isBlank(schemaName) || StringUtils.isBlank(domainName) || StringUtils.isBlank(shortUrl)) {
            throw new IllegalArgumentException("Schema, domain, and short URL cannot be blank.");
        }

        return String.format("%s://%s/%s", schemaName, domainName, shortUrl);
    }

    /**
     * Parses a URL string into a URI object.
     *
     * @param url the URL string
     * @return the URI object
     * @throws DomainNameException if the URL string is invalid
     */
    private static URI parseUri(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new DomainNameException("Invalid URL: " + url + ". Error: " + e.getMessage());
        }
    }
}
