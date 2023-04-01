package com.thefilipov.food.api.controller;

import static org.mockito.Mockito.doNothing;

import com.thefilipov.food.api.assembler.UsuarioModelAssembler;
import com.thefilipov.food.domain.service.CadastroRestauranteService;
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

@ContextConfiguration(classes = {RestauranteUsuarioResponsavelController.class})
@ExtendWith(SpringExtension.class)
class RestauranteUsuarioResponsavelControllerTest {
    @MockBean
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private RestauranteUsuarioResponsavelController restauranteUsuarioResponsavelController;

    @MockBean
    private UsuarioModelAssembler usuarioModelAssembler;

    /**
     * Method under test: {@link RestauranteUsuarioResponsavelController#associar(Long, Long)}
     */
    @Test
    void testAssociar() throws Exception {
        doNothing().when(cadastroRestauranteService).associarResponsavel(Mockito.<Long>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/restaurantes/{restauranteId}/responsaveis/{usuarioId}", 1L, 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(restauranteUsuarioResponsavelController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link RestauranteUsuarioResponsavelController#associar(Long, Long)}
     */
    @Test
    void testAssociar2() throws Exception {
        doNothing().when(cadastroRestauranteService).associarResponsavel(Mockito.<Long>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/restaurantes/{restauranteId}/responsaveis/{usuarioId}", 1L, 1L);
        requestBuilder.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(restauranteUsuarioResponsavelController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

