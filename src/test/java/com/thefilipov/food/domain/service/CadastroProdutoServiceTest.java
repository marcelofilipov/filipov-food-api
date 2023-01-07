package com.thefilipov.food.domain.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.exception.ProdutoNaoEncontradoException;
import com.thefilipov.food.domain.model.Produto;
import com.thefilipov.food.domain.repository.ProdutoRepository;
import com.thefilipov.food.templates.ProdutoTemplates;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@DisplayName("Teste Unitário da class CadastroProdutoService")
class CadastroProdutoServiceTest extends ApplicationConfigTest {

    private static final long ID = 1L;
    private static final String NAME = "Porco com molho agridoce";
    private static final String PRODUTO_NAO_ENCONTRADA =
            "Não existe um cadastro de produto com código 1 para o restaurante de código 1";
    @InjectMocks
    private CadastroProdutoService service;

    @Mock
    private ProdutoRepository repository;

    private Produto saveProduto;
    private Optional<Produto> oneProduto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        FixtureFactoryLoader.loadTemplates(ProdutoTemplates.class.getPackage().getName());
        startProduto();
    }

    @Disabled
    @Test
    @DisplayName("Buscar um Produto")
    void whenFindByIdThenReturnProdutoInstance() {
        when(repository.findById(anyLong())).thenReturn(oneProduto);

        Produto response = service.buscarOuFalhar(ID, ID);

        assertAll(() -> assertNotNull(response),
            () -> assertEquals(Produto.class, response.getClass()),
            () -> assertEquals(ID, response.getId()),
            () -> assertEquals(NAME, response.getNome())
        );
    }

    @Test
    @DisplayName("Retorna uma exceção quando não encontrar Produto")
    void whenFindByIdThenReturnProdutoNaoEncontradaException() {
        when(repository.findById(anyLong())).thenThrow(new ProdutoNaoEncontradoException(ID, ID));

        try {
            service.buscarOuFalhar(ID, ID);
        } catch (Exception ex) {
            assertAll(
                () -> assertEquals(ProdutoNaoEncontradoException.class, ex.getClass()),
                () -> assertEquals(PRODUTO_NAO_ENCONTRADA, ex.getMessage())
            );
        }
    }

    @RepeatedTest(value = 3)
    @DisplayName("Insere um Produto")
    void whenCreateThenReturnSuccess() {
        when(repository.save((Produto) any())).thenReturn(saveProduto);

        Produto response = service.salvar(saveProduto);

        assertNotNull(response);
        assertEquals(Produto.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getNome());
    }

    private void startProduto() {
        saveProduto = Fixture.from(Produto.class).gimme("oneProduto");
        oneProduto = Optional.of(Fixture.from(Produto.class).gimme("oneProduto"));
    }

}