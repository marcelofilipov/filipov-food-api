package com.thefilipov.food.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.api.model.input.CozinhaInput;
import com.thefilipov.food.domain.exception.CozinhaNaoEncontradaException;
import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.service.CadastroCozinhaService;
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

public class CozinhaControllerTest extends ApplicationConfigTest {
	
	private static final long ID = 1L;
	private static final String NAME = "Tailandesa";

	private static final String COZINHA_NAO_ENCONTRADA = "Não existe uma Cozinha cadastrada com o código 1";

	private CozinhaInput cozinhaInput;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private CadastroCozinhaService cozinhaService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startCozinha();
	}

	@Test
	@DisplayName("Retornar success(200) - Quando buscar todas as cozinhas")
	void getAllCozinhasAPI_RetornarStatus200() throws Exception {
		this.mockMvc.perform(get(CozinhaController.URI)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(5)));
	}

	@Test
	@DisplayName("Retornar success(200) - Quando buscar 1 cozinha")
	void getCozinhaByIdAPI_RetornarStatus200() throws Exception {
		this.mockMvc.perform(get(CozinhaController.URI + "/{cozinhaId}", ID)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(ID))
				.andExpect(jsonPath("$.nome").value(NAME));
	}

	@Test
	@DisplayName("Retornar created(201) - Quando criar cozinha")
	void createCozinhaAPI_RetornarStatus201() throws Exception {
		this.mockMvc.perform(post(CozinhaController.URI)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(cozinhaInput)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome").value(cozinhaInput.getNome()));
	}

	@Test
	@DisplayName("Retornar success(200) - Quando alterar cozinha")
	void putCozinhaAPI_RetornarStatus200() throws Exception {
		Long cozinhaId = 2L;

		this.mockMvc.perform(put(CozinhaController.URI + "/{cozinhaId}", cozinhaId)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(cozinhaInput)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome").value(cozinhaInput.getNome()));
	}

	@Test
	@DisplayName("Retornar noContent(204) - Quando remover cozinha existente")
	public void deveRetornarStatusNoContentQuandoRemoverCozinhaExistente() throws Exception {
		Long cozinhaId = 5L;

		this.mockMvc.perform(delete(CozinhaController.URI + "/{cozinhaId}", cozinhaId))
				.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("Retornar notFound(404) - Quando tentar remover cozinha inexistente")
	public void deveRetornarStatusNotFoundQuandoRemoverCozinhaInexistente() throws Exception {
		Long cozinhaId = 100L;

		doThrow(CozinhaNaoEncontradaException.class).when(cozinhaService).excluir(cozinhaId);

		this.mockMvc.perform(delete(CozinhaController.URI + "/{cozinhaId}", cozinhaId))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Retornar conflict(409) - Quando tentar remover cozinha em uso")
	public void deveRetornarStatusBadRequestQuandoRemoverCozinhaEmUso() throws Exception {
		Long cozinhaId = 1L;

		doThrow(EntidadeEmUsoException.class).when(cozinhaService).excluir(cozinhaId);

		this.mockMvc.perform(delete(CozinhaController.URI + "/{cozinhaId}", cozinhaId))
				.andExpect(status().isConflict());
	}

	private void startCozinha() {
		cozinhaInput = new CozinhaInput();
		cozinhaInput.setNome("Italiana");
	}

}
