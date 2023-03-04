package com.thefilipov.food.core.data;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PageableTranslatorTest {

    @Test
    void testTranslate() {
        // Setup
        final Map<String, String> fieldsMapping = Map.ofEntries(Map.entry("value", "value"));

        // Run the test
        final Pageable result = PageableTranslator.translate(PageRequest.of(0, 1), fieldsMapping);

        // Verify the results
        assertNotNull(result);
    }
}
