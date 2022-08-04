package com.thefilipov.food.domain.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.exception.UsuarioNaoEncontradoException;
import com.thefilipov.food.domain.model.Usuario;
import com.thefilipov.food.domain.repository.UsuarioRepository;
import com.thefilipov.food.templates.UsuarioTemplates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@DisplayName("Teste Unitário da class CadastroUsuarioService")
class CadastroUsuarioServiceTest extends ApplicationConfigTest {

    private static final long ID = 1L;
    private static final long NONEXISTENT = 100L;
    private static final String NAME = "Maria Joaquina";
    private static final String EMAIL = "maria.vnd@filfood.com";
    private static final String SENHA = "123";
    private static final String USUARIO_NAO_ENCONTRADO = "Não existe um Usuário cadastrado com o código 1";

    @InjectMocks
    private CadastroUsuarioService service;

    @Mock
    private UsuarioRepository repository;

    private Usuario saveUsuario;
    private Optional<Usuario> oneUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        FixtureFactoryLoader.loadTemplates(UsuarioTemplates.class.getPackage().getName());
        startUsuario();
    }

    @Test
    @DisplayName("Buscar um Usuário")
    void whenFindByIdThenReturnUsuarioInstance() {
        when(repository.findById(anyLong())).thenReturn(oneUsuario);

        Usuario response = service.buscarOuFalhar(ID);

        assertAll(() -> assertNotNull(response),
                () -> assertEquals(Usuario.class, response.getClass()),
                () -> assertEquals(ID, response.getId()),
                () -> assertEquals(NAME, response.getNome()),
                () -> assertEquals(EMAIL, response.getEmail()),
                () -> assertEquals(SENHA, response.getSenha())
        );
    }

    @Test
    @DisplayName("Retorna uma exceção quando não encontrar Usuario")
    void whenFindByIdThenReturnUsuarioNaoEncontradoException() {
        when(repository.findById(anyLong())).thenThrow(new UsuarioNaoEncontradoException(ID));

        try {
            service.buscarOuFalhar(ID);
        } catch (Exception ex) {
            assertAll(
                    () -> assertEquals(UsuarioNaoEncontradoException.class, ex.getClass()),
                    () -> assertEquals(USUARIO_NAO_ENCONTRADO, ex.getMessage())
            );
        }
    }

    @Test
    void alterarSenha() {
    }

    @Test
    void desassociarGrupo() {
    }

    @Test
    void associarGrupo() {
    }

    private void startUsuario() {
        oneUsuario = Optional.of(Fixture.from(Usuario.class).gimme("oneUsuario"));
    }

}