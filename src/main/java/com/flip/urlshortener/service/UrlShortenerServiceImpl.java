package com.flip.urlshortener.service;

import com.flip.urlshortener.exception.UrlNotFoundException;
import com.flip.urlshortener.utils.Base62EncoderHelper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of the {@link UrlShortenerService} interface.
 * Provides functionality to shorten URLs and retrieve the original URLs from shortened ones.
 */
@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final Map<String, String> urlMap = new ConcurrentHashMap<>();
    private final Map<String, String> reverseUrlMap = new ConcurrentHashMap<>();

    /**
     * Shortens a given long URL.
     * Uses a concurrent map to ensure thread safety and caching for efficiency.
     *
     * @param longUrl the original long URL to be shortened
     * @return the shortened URL
     */
    @Override
    @CachePut(value = "shortUrls", key = "#longUrl")
    public String shortenUrl(String longUrl) {
        return reverseUrlMap.computeIfAbsent(longUrl, url -> {
            String shortUrl = Base62EncoderHelper.encode(System.nanoTime());
            String domainName = Base62EncoderHelper.getDomainName(longUrl);
            String schemaName = Base62EncoderHelper.getSchema(longUrl);
            String shortUrlWithDomainName = Base62EncoderHelper.createShortUrl(schemaName, domainName, shortUrl);
            urlMap.putIfAbsent(shortUrlWithDomainName, longUrl);
            return shortUrlWithDomainName;
        });
    }

    /**
     * Retrieves the original long URL for a given shortened URL.
     * Uses caching to improve performance and provides an exception if the URL is not found.
     *
     * @param shortUrl the shortened URL
     * @return the original long URL
     * @throws UrlNotFoundException if the shortened URL does not exist
     */
    @Override
    @Cacheable(value = "longUrls", key = "#shortUrl")
    public String getOriginalUrl(String shortUrl) {
        return Optional.ofNullable(urlMap.get(shortUrl))
                .orElseThrow(() -> new UrlNotFoundException("URL not found"));
    }
}
