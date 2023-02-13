package com.thefilipov.food.infrastructure.repository.spec;

import com.thefilipov.food.domain.filter.PedidoFilter;
import com.thefilipov.food.domain.model.Pedido;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PedidoSpecsTest {

    @Test
    void testUsandoFiltro() {
        // Setup
        final PedidoFilter filtro = new PedidoFilter();
        filtro.setClienteId(0L);
        filtro.setRestauranteId(0L);
        filtro.setDataCriacaoInicio(OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC));
        filtro.setDataCriacaoFim(OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC));

        // Run the test
        final Specification<Pedido> result = PedidoSpecs.usandoFiltro(filtro);

        // Verify the results
        assertNotNull(result);
    }
}
