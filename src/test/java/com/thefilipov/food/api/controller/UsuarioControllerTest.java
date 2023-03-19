package com.thefilipov.food.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.api.assembler.UsuarioInputDisassembler;
import com.thefilipov.food.api.assembler.UsuarioModelAssembler;
import com.thefilipov.food.api.model.UsuarioModel;
import com.thefilipov.food.api.model.input.SenhaInput;
import com.thefilipov.food.api.model.input.UsuarioComSenhaInput;
import com.thefilipov.food.api.model.input.UsuarioInput;
import com.thefilipov.food.domain.model.Usuario;
import com.thefilipov.food.domain.repository.UsuarioRepository;
import com.thefilipov.food.domain.service.CadastroUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UsuarioControllerTest extends ApplicationConfigTest {

    private static final long ID = 1L;
    public static final String NOME = "Nome";
    public static final String EMAIL = "jane.doe@example.org";
    public static final String SENHA = "Senha";

    private static final String USUARIO_NAO_ENCONTRADO = "Não existe Usuário cadastrado com o código 1";

    @InjectMocks
    private UsuarioController usuarioController;

    @MockBean
    private CadastroUsuarioService cadastroUsuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioModelAssembler usuarioModelAssembler;

    @Mock
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method under test: {@link UsuarioController#listar()}
     */
    @Test
    @DisplayName("Retornar success(200) - Quando buscar todos os usuários")
    void getAllUsuariosAPI_RetornarStatus200() throws Exception {
        when(usuarioModelAssembler.toCollectionModel((Collection<Usuario>) any())).thenReturn(new ArrayList<>());
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(UsuarioController.URI);
        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }

    /**
     * Method under test: {@link UsuarioController#buscar(Long)}
     */
    @Test
    @DisplayName("Retornar success(200) - Quando buscar 1 usuário")
    void getUsuarioByIdAPI_RetornarStatus200() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setDataCadastro(null);
        usuario.setEmail(EMAIL);
        usuario.setGrupos(new HashSet<>());
        usuario.setId(1L);
        usuario.setNome(NOME);
        usuario.setSenha(SENHA);
        when(cadastroUsuarioService.buscarOuFalhar((Long) any())).thenReturn(usuario);

        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setEmail(EMAIL);
        usuarioModel.setId(1L);
        usuarioModel.setNome(NOME);
        when(usuarioModelAssembler.toModel((Usuario) any())).thenReturn(usuarioModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(UsuarioController.URI + "/{usuarioId}", 1L);
        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("<UsuarioModel><id>1</id><nome>Nome</nome><email>jane.doe@example.org</email></UsuarioModel>"));
    }

    /**
     * Method under test: {@link UsuarioController#adicionar(UsuarioComSenhaInput)}
     */
    @Test
    @DisplayName("Retornar created(201) - Quando criar usuário")
    void createUsuarioAPI_RetornarStatus201() throws Exception {
        when(usuarioModelAssembler.toCollectionModel((Collection<Usuario>) any())).thenReturn(new ArrayList<>());
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        UsuarioComSenhaInput usuarioComSenhaInput = new UsuarioComSenhaInput();
        usuarioComSenhaInput.setEmail(EMAIL);
        usuarioComSenhaInput.setNome(NOME);
        usuarioComSenhaInput.setSenha(SENHA);
        String content = (new ObjectMapper()).writeValueAsString(usuarioComSenhaInput);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    /**
     * Method under test: {@link UsuarioController#atualizar(Long, UsuarioInput)}
     */
    @Test
    @DisplayName("Retornar success(200) - Quando alterar usuário")
    void putUsuarioAPI_RetornarStatus200() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setDataCadastro(null);
        usuario.setEmail(EMAIL);
        usuario.setGrupos(new HashSet<>());
        usuario.setId(1L);
        usuario.setNome(NOME);
        usuario.setSenha(SENHA);

        Usuario usuario1 = new Usuario();
        usuario1.setDataCadastro(null);
        usuario1.setEmail(EMAIL);
        usuario1.setGrupos(new HashSet<>());
        usuario1.setId(1L);
        usuario1.setNome(NOME);
        usuario1.setSenha(SENHA);
        when(cadastroUsuarioService.buscarOuFalhar((Long) any())).thenReturn(usuario);
        when(cadastroUsuarioService.salvar((Usuario) any())).thenReturn(usuario1);
        doNothing().when(usuarioInputDisassembler).copyToDomainObject((UsuarioInput) any(), (Usuario) any());

        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setEmail(EMAIL);
        usuarioModel.setId(1L);
        usuarioModel.setNome(NOME);
        when(usuarioModelAssembler.toModel((Usuario) any())).thenReturn(usuarioModel);

        UsuarioInput usuarioInput = new UsuarioInput();
        usuarioInput.setEmail(EMAIL);
        usuarioInput.setNome(NOME);
        String content = (new ObjectMapper()).writeValueAsString(usuarioInput);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/usuarios/{usuarioId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("<UsuarioModel><id>1</id><nome>Nome</nome><email>jane.doe@example.org</email></UsuarioModel>"));
    }

    /**
     * Method under test: {@link UsuarioController#listar()}
     */
    @Test
    @DisplayName("Retornar success(200) - Quando buscar todos os usuários com encoding")
    void getAllUsuariosAPI_RetornarStatus200WithEncoding() throws Exception {
        when(usuarioModelAssembler.toCollectionModel((Collection<Usuario>) any())).thenReturn(new ArrayList<>());
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get(UsuarioController.URI);
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }

    /**
     * Method under test: {@link UsuarioController#alterarSenha(Long, SenhaInput)}
     */
    @Test
    @DisplayName("Retornar success(204) - Quando alterar senha")
    void putSenhaAPI_RetornarStatus204() throws Exception {
        doNothing().when(cadastroUsuarioService).alterarSenha((Long) any(), (String) any(), (String) any());

        SenhaInput senhaInput = new SenhaInput();
        senhaInput.setNovaSenha("Nova Senha");
        senhaInput.setSenhaAtual("Senha Atual");
        String content = (new ObjectMapper()).writeValueAsString(senhaInput);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(UsuarioController.URI + "/{usuarioId}/senha", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}