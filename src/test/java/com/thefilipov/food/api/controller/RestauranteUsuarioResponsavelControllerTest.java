package com.thefilipov.food.api.controller;

import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.api.assembler.UsuarioModelAssembler;
import com.thefilipov.food.domain.model.*;
import com.thefilipov.food.domain.service.CadastroRestauranteService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RestauranteUsuarioResponsavelController.class})
class RestauranteUsuarioResponsavelControllerTest extends ApplicationConfigTest {
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
    void testAssociarWithEncoding() throws Exception {
        doNothing().when(cadastroRestauranteService).associarResponsavel(Mockito.<Long>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/restaurantes/{restauranteId}/responsaveis/{usuarioId}", 1L, 1L);
        requestBuilder.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(restauranteUsuarioResponsavelController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link RestauranteUsuarioResponsavelController#desassociar(Long, Long)}
     */
    @Test
    void testDesassociar() throws Exception {
        doNothing().when(cadastroRestauranteService).desassociarResponsavel(Mockito.<Long>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/restaurantes/{restauranteId}/responsaveis/{usuarioId}", 1L, 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(restauranteUsuarioResponsavelController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link RestauranteUsuarioResponsavelController#desassociar(Long, Long)}
     */
    @Test
    void testDesassociarWithEncoding() throws Exception {
        doNothing().when(cadastroRestauranteService).desassociarResponsavel(Mockito.<Long>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/restaurantes/{restauranteId}/responsaveis/{usuarioId}", 1L, 1L);
        requestBuilder.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(restauranteUsuarioResponsavelController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link RestauranteUsuarioResponsavelController#listar(Long)}
     */
    @Test
    @DisplayName("Retornar success(200) - Quando buscar 1 restaurante")
    @Disabled
    void getListarByIdAPI_RetornarStatus200() throws Exception {
        Cozinha cozinha = new Cozinha();
        cozinha.setId(1L);
        cozinha.setNome(UsuarioControllerTest.NOME);
        cozinha.setRestaurantes(new ArrayList<>());

        Estado estado = new Estado();
        estado.setId(1L);
        estado.setNome(UsuarioControllerTest.NOME);

        Cidade cidade = new Cidade();
        cidade.setEstado(estado);
        cidade.setId(1L);
        cidade.setNome(UsuarioControllerTest.NOME);

        Endereco endereco = new Endereco();
        endereco.setBairro("Bairro");
        endereco.setCep("Cep");
        endereco.setCidade(cidade);
        endereco.setComplemento("alice.liddell@example.org");
        endereco.setLogradouro("Logradouro");
        endereco.setNumero("Numero");

        Restaurante restaurante = new Restaurante();
        restaurante.setAberto(true);
        restaurante.setAtivo(true);
        restaurante.setCozinha(cozinha);
        restaurante.setDataAtualizacao(OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
        restaurante.setDataCadastro(OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
        restaurante.setEndereco(endereco);
        restaurante.setFormasPagamento(new HashSet<>());
        restaurante.setId(1L);
        restaurante.setNome(UsuarioControllerTest.NOME);
        restaurante.setProdutos(new ArrayList<>());
        restaurante.setResponsaveis(new HashSet<>());
        restaurante.setTaxaFrete(BigDecimal.valueOf(42L));

        when(cadastroRestauranteService.buscarOuFalhar(Mockito.<Long>any())).thenReturn(restaurante);
        //when(usuarioModelAssembler.toCollectionModel(Mockito.<Collection<Usuario>>any())).thenReturn(Collections.singletonList(new UsuarioModel()));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/restaurantes/{restauranteId}/responsaveis", 1L);
        MockMvcBuilders.standaloneSetup(restauranteUsuarioResponsavelController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }
}

