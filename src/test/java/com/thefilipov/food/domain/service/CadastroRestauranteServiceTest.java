package com.thefilipov.food.domain.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.exception.RestauranteNaoEncontradoException;
import com.thefilipov.food.domain.model.Restaurante;
import com.thefilipov.food.domain.repository.RestauranteRepository;
import com.thefilipov.food.templates.RestauranteTemplates;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@DisplayName("Teste Unitário da class CadastroRestauranteService")
class CadastroRestauranteServiceTest extends ApplicationConfigTest {

    private static final long ID = 1L;
    private static final String NAME = "Restaurante Sabor do Brasil";

    private static final String RESTAURANTE_NAO_ENCONTRADA = "Não existe um Restaurante cadastrado com o código 1";

    @InjectMocks
    private CadastroRestauranteService service;

    @Mock
    private CadastroCozinhaService cozinhaService;

    @Mock
    private CadastroCidadeService cidadeService;

    @Mock
    private RestauranteRepository repository;

    private Restaurante saveRestaurante;
    private Optional<Restaurante> oneRestaurante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        FixtureFactoryLoader.loadTemplates(RestauranteTemplates.class.getPackage().getName());
        startRestaurante();
    }

    @Test
    @DisplayName("Buscar um Restaurante")
    void whenFindByIdThenReturnRestauranteInstance() {
        when(repository.findById(anyLong())).thenReturn(oneRestaurante);

        Restaurante response = service.buscarOuFalhar(ID);

        assertAll(() -> assertNotNull(response),
            () -> assertEquals(Restaurante.class, response.getClass()),
            () -> assertEquals(ID, response.getId()),
            () -> assertEquals(NAME, response.getNome()));
    }

    @Test
    @DisplayName("Retorna uma exceção quando não encontrar Restaurante")
    void whenFindByIdThenReturnRestauranteNaoEncontradaException() {
        when(repository.findById(anyLong())).thenThrow(new RestauranteNaoEncontradoException(ID));

        try {
            service.buscarOuFalhar(ID);
        } catch (Exception ex) {
            assertAll(
                () -> assertEquals(RestauranteNaoEncontradoException.class, ex.getClass()),
                () -> assertEquals(RESTAURANTE_NAO_ENCONTRADA, ex.getMessage())
            );
        }
    }

    @RepeatedTest(value = 3)
    @DisplayName("Insere um Restaurante")
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(saveRestaurante);

        Restaurante response = service.salvar(saveRestaurante);

        assertNotNull(response);
        assertEquals(Restaurante.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getNome());
    }

    private void startRestaurante() {
        saveRestaurante = Fixture.from(Restaurante.class).gimme("oneRestaurante");
        oneRestaurante = Optional.of(Fixture.from(Restaurante.class).gimme("oneRestaurante"));
    }

}
