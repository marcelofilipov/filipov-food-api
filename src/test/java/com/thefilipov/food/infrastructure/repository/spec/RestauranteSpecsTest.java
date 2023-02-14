package com.thefilipov.food.infrastructure.repository.spec;

import com.thefilipov.food.domain.model.Restaurante;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestauranteSpecsTest {

    @Test
    void testComFreteGratis() {
        // Setup
        // Run the test
        final Specification<Restaurante> result = RestauranteSpecs.comFreteGratis();

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testComNomeSemelhante() {
        // Setup
        // Run the test
        final Specification<Restaurante> result = RestauranteSpecs.comNomeSemelhante("nome");

        // Verify the results
        assertNotNull(result);
    }
}
