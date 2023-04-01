package com.thefilipov.food.api.controller;

import static org.mockito.Mockito.doNothing;

import com.thefilipov.food.api.assembler.GrupoModelAssembler;
import com.thefilipov.food.domain.service.CadastroUsuarioService;
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
    void testAssociar2() throws Exception {
        doNothing().when(cadastroUsuarioService).associarGrupo(Mockito.<Long>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/usuarios/{usuarioId}/grupos/{grupoId}", 1L, 1L);
        requestBuilder.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usuarioGrupoController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

