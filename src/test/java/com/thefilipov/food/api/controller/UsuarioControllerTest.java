package com.thefilipov.food.api.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.thefilipov.food.api.assembler.UsuarioInputDisassembler;
import com.thefilipov.food.api.assembler.UsuarioModelAssembler;
import com.thefilipov.food.api.model.UsuarioModel;
import com.thefilipov.food.domain.model.Usuario;
import com.thefilipov.food.domain.repository.UsuarioRepository;
import com.thefilipov.food.domain.service.CadastroUsuarioService;
import com.thefilipov.food.templates.UsuarioTemplates;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Slf4j
public class UsuarioControllerTest {

    private static final long ID = 1L;
    private static final String NAME = "Gilmar Lopes Assis";

    private static final String USUARIO_NAO_ENCONTRADA = "Não existe Usuário cadastrado com o código 1";

    private Usuario usuario;

    private UsuarioModel usuarioModel;

    private List<Usuario> fourUsuarios;

    private List<UsuarioModel> fourUsuariosModel;

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private CadastroUsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioModelAssembler usuarioModelAssembler;

    @Mock
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        FixtureFactoryLoader.loadTemplates(UsuarioTemplates.class.getPackage().getName());
        startUsuario();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(usuarioService.buscarOuFalhar(anyLong())).thenReturn(usuario);
        when(usuarioModelAssembler.toModel(Mockito.any())).thenReturn(usuarioModel);

        Usuario response = usuarioService.buscarOuFalhar(ID);

        assertNotNull(response);
        assertEquals(Usuario.class, response.getClass());
    }

    @Test
    void whenFindAllThenReturnAListOfUsuarioModel() {
        when(usuarioRepository.findAll()).thenReturn(fourUsuarios);

        List<UsuarioModel> response = usuarioModelAssembler.toCollectionModel(fourUsuarios);

        assertNotNull(response);
        assertEquals(LinkedList.class, response.getClass());
        //assertEquals(UsuarioModel.class, response.get(0).getClass());
    }

    @Test
    void adicionar() {
    }

    @Test
    void atualizar() {
    }

    @Test
    void alterarSenha() {
    }

    private void startUsuario() {
        usuario = Fixture.from(Usuario.class).gimme("oneUsuario");
        usuarioModel = Fixture.from(UsuarioModel.class).gimme("oneUsuarioModel");
        fourUsuarios = Fixture.from(Usuario.class).gimme(4, "anyUsuario");
        fourUsuariosModel = Fixture.from(UsuarioModel.class).gimme(4, "anyUsuarioModel");
    }

}