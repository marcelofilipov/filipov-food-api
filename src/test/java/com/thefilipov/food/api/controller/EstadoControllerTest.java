package com.thefilipov.food.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.api.model.input.EstadoInput;
import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.exception.EstadoNaoEncontradoException;
import com.thefilipov.food.domain.service.CadastroEstadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EstadoControllerTest extends ApplicationConfigTest {

    private static final long ID = 1L;
    private static final String NAME = "Minas Gerais";


    private EstadoInput estadoInput;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CadastroEstadoService estadoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startEstado();
    }

    @Test
    @DisplayName("Retornar success(200) - Quando buscar todos os estados")
    void getAllEstadosAPI_RetornarStatus200() throws Exception {
        this.mockMvc.perform(get(EstadoController.URI)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(6)));
    }

    @Test
    @DisplayName("Retornar success(200) - Quando buscar 1 estado")
    void getEstadoByIdAPI_RetornarStatus200() throws Exception {
        this.mockMvc.perform(get(EstadoController.URI + "/{estadoId}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.nome").value(NAME));
    }

    @Test
    @DisplayName("Retornar created(201) - Quando criar estado")
    void createEstadoAPI_RetornarStatus201() throws Exception {
        this.mockMvc.perform(post(EstadoController.URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(estadoInput)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(estadoInput.getNome()));
    }

    @Test
    @DisplayName("Retornar success(200) - Quando alterar estado")
    void putEstadoAPI_RetornarStatus200() throws Exception {
        Long estadoId = 2L;

        this.mockMvc.perform(put(EstadoController.URI + "/{estadoId}", estadoId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estadoInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(estadoInput.getNome()));
    }

    @Test
    @DisplayName("Retornar noContent(204) - Quando remover estado existente")
    public void deveRetornarStatusNoContentQuandoRemoverEstadoExistente() throws Exception {
        Long estadoId = 99L;

        this.mockMvc.perform(delete(EstadoController.URI + "/{estadoId}", estadoId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Retornar notFound(404) - Quando tentar remover estado inexistente")
    public void deveRetornarStatusNotFoundQuandoRemoverEstadoInexistente() throws Exception {
        Long estadoId = 100L;

        doThrow(EstadoNaoEncontradoException.class).when(estadoService).excluir(estadoId);

        this.mockMvc.perform(delete(CozinhaController.URI + "/{estadoId}", estadoId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Retornar conflict(409) - Quando tentar remover estado em uso")
    public void deveRetornarStatusBadRequestQuandoRemoverEstadoEmUso() throws Exception {
        Long estadoId = 1L;

        doThrow(EntidadeEmUsoException.class).when(estadoService).excluir(estadoId);

        this.mockMvc.perform(delete(EstadoController.URI + "/{estadoId}", estadoId))
                .andExpect(status().isConflict());
    }

    private void startEstado() {
        estadoInput = new EstadoInput();
        estadoInput.setNome("São Paulo");
    }

}
