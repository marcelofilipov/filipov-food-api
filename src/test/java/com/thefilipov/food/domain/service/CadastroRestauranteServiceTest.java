package com.thefilipov.food.domain.service;

import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.model.Restaurante;
import com.thefilipov.food.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

@DisplayName("Teste Unitário da class CadastroRestauranteService")
class CadastroRestauranteServiceTest extends ApplicationConfigTest {

    private static final long ID = 1L;
    private static final String NAME = "Restaurante Sabor do Brasil";

    @InjectMocks
    private CadastroRestauranteService service;

    @Mock
    private RestauranteRepository repository;

    private Restaurante restaurante;
    private Optional<Restaurante> optionalRestaurante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startRestaurante();
    }

    @Test
    @DisplayName("Buscar um Restaurante")
    void whenFindByIdThenReturnRestauranteInstance() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(optionalRestaurante);

        Restaurante response = service.buscarOuFalhar(ID);

        Assertions.assertAll(() -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals(Restaurante.class, response.getClass()),
                () -> Assertions.assertEquals(ID, response.getId()),
                () -> Assertions.assertEquals(NAME, response.getNome()));
    }

    private void startRestaurante() {
        restaurante = new Restaurante(ID, NAME, new BigDecimal(9.99), OffsetDateTime.now(), OffsetDateTime.now(), true);
        optionalRestaurante = Optional.of(new Restaurante(ID, NAME, new BigDecimal(9.99), OffsetDateTime.now(), OffsetDateTime.now(), true));
    }

}
