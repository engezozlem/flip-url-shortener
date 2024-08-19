package com.flip.urlshortener.utils;

import com.flip.urlshortener.exception.DomainNameException;
import com.flip.urlshortener.exception.SchemaNameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Base62EncoderHelperTest {

    @Test
    void testEncodeWithZero() {
        assertThrows(IllegalArgumentException.class, () -> Base62EncoderHelper.encode(0));
    }

    @Test
    void testGetDomainName() {
        assertEquals("example.com", Base62EncoderHelper.getDomainName("https://www.example.com"));
        assertEquals("example.com", Base62EncoderHelper.getDomainName("http://example.com"));
        assertEquals("sub.example.com", Base62EncoderHelper.getDomainName("https://sub.example.com"));
    }

    @Test
    void testGetDomainNameWithInvalidUrl() {
        assertThrows(DomainNameException.class, () -> Base62EncoderHelper.getDomainName("invalid-url"));
        assertThrows(DomainNameException.class, () -> Base62EncoderHelper.getDomainName("https:///example.com"));
    }

    @Test
    void testGetSchema() {
        assertEquals("https", Base62EncoderHelper.getSchema("https://www.example.com"));
        assertEquals("http", Base62EncoderHelper.getSchema("http://example.com"));
    }

    @Test
    void testGetSchemaWithInvalidUrl() {
        assertThrows(DomainNameException.class, () -> Base62EncoderHelper.getSchema("://example.com"));
        assertThrows(SchemaNameException.class, () -> Base62EncoderHelper.getSchema("invalid-url"));
    }

    @Test
    void testCreateShortUrl() {
        String shortUrl = Base62EncoderHelper.createShortUrl("https", "example.com", "abc123");
        assertEquals("https://example.com/abc123", shortUrl);
    }

    @Test
    void testCreateShortUrlWithInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> Base62EncoderHelper.createShortUrl("", "example.com", "abc123"));
        assertThrows(IllegalArgumentException.class, () -> Base62EncoderHelper.createShortUrl("https", "", "abc123"));
        assertThrows(IllegalArgumentException.class, () -> Base62EncoderHelper.createShortUrl("https", "example.com", ""));
    }
}
