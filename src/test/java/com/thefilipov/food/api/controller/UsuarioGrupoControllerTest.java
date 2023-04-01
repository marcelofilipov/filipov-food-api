package com.thefilipov.food.api.controller;

import com.thefilipov.food.api.assembler.GrupoModelAssembler;
import com.thefilipov.food.domain.model.Grupo;
import com.thefilipov.food.domain.model.Usuario;
import com.thefilipov.food.domain.service.CadastroUsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UsuarioGrupoController.class})
@ExtendWith(SpringExtension.class)
class UsuarioGrupoControllerTest {
    @MockBean
    private CadastroUsuarioService cadastroUsuarioService;

    @MockBean
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private UsuarioGrupoController usuarioGrupoController;

    /**
     * Method under test: {@link UsuarioGrupoController#associar(Long, Long)}
     */
    @Test
    void testAssociar() throws Exception {
        doNothing().when(cadastroUsuarioService).associarGrupo(Mockito.<Long>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/usuarios/{usuarioId}/grupos/{grupoId}",
                1L, 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioGrupoController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link UsuarioGrupoController#associar(Long, Long)}
     */
    @Test
    void testAssociarWithEncoding() throws Exception {
        doNothing().when(cadastroUsuarioService).associarGrupo(Mockito.<Long>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/usuarios/{usuarioId}/grupos/{grupoId}", 1L, 1L);
        requestBuilder.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioGrupoController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link UsuarioGrupoController#desassociar(Long, Long)}
     */
    @Test
    void testDesassociar() throws Exception {
        doNothing().when(cadastroUsuarioService).desassociarGrupo(Mockito.<Long>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/usuarios/{usuarioId}/grupos/{grupoId}", 1L, 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioGrupoController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link UsuarioGrupoController#desassociar(Long, Long)}
     */
    @Test
    void testDesassociarWithEncoding() throws Exception {
        doNothing().when(cadastroUsuarioService).desassociarGrupo(Mockito.<Long>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/usuarios/{usuarioId}/grupos/{grupoId}", 1L, 1L);
        requestBuilder.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioGrupoController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link UsuarioGrupoController#listar(Long)}
     */
    @Test
    @DisplayName("Retornar success(200) - Quando buscar 1 grupo")
    void getListarByIdAPI_RetornarStatus200() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setDataCadastro(OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
        usuario.setEmail(UsuarioControllerTest.EMAIL);
        usuario.setGrupos(new HashSet<>());
        usuario.setId(1L);
        usuario.setNome(UsuarioControllerTest.NOME);
        usuario.setSenha(UsuarioControllerTest.SENHA);
        when(cadastroUsuarioService.buscarOuFalhar(Mockito.<Long>any())).thenReturn(usuario);
        when(grupoModelAssembler.toCollectionModel(Mockito.<Collection<Grupo>>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/usuarios/{usuarioId}/grupos", 1L);
        MockMvcBuilders.standaloneSetup(usuarioGrupoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }
}

