package com.thefilipov.food.api.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.thefilipov.food.api.assembler.CozinhaInputDisassembler;
import com.thefilipov.food.api.assembler.CozinhaModelAssembler;
import com.thefilipov.food.api.model.CozinhaModel;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.repository.CozinhaRepository;
import com.thefilipov.food.domain.service.CadastroCozinhaService;
import com.thefilipov.food.templates.CozinhaTemplates;
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

public class CozinhaControllerTest {
	
	private static final long ID = 1L;
	private static final String NAME = "Brasileira";

	private static final String COZINHA_NAO_ENCONTRADA = "Não existe uma Cozinha cadastrada com o código 1";

	private Cozinha cozinha;
	private CozinhaModel cozinhaModel;
	private List<Cozinha> fourCozinhas;

	@InjectMocks
	private CozinhaController cozinhaController;

	@Mock
	private CadastroCozinhaService cozinhaService;
	
	@Mock
	private CozinhaRepository cozinhaRepository;
	
	@Mock
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Mock
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		FixtureFactoryLoader.loadTemplates(CozinhaTemplates.class.getPackage().getName());
		startCozinha();
	}

	@Test
	void whenFindByIdThenReturnSuccess() {
		when(cozinhaService.buscarOuFalhar(anyLong())).thenReturn(cozinha);
		when(cozinhaModelAssembler.toModel(Mockito.any())).thenReturn(cozinhaModel);
		
		Cozinha response = cozinhaService.buscarOuFalhar(ID);
		
		assertNotNull(response);
		assertEquals(Cozinha.class, response.getClass());
	}

	@Test
	void whenFindAllThenReturnAListOfCozinhaModel() {
		when(cozinhaRepository.findAll()).thenReturn(fourCozinhas);
		
		List<CozinhaModel> response = cozinhaModelAssembler.toCollectionModel(fourCozinhas);
		
		assertNotNull(response);
		assertEquals(LinkedList.class, response.getClass());
		//assertEquals(CozinhaModel.class, response.get(0).getClass());
	}
	
	private void startCozinha() {
		cozinha = Fixture.from(Cozinha.class).gimme("oneCozinha");
		cozinhaModel = Fixture.from(CozinhaModel.class).gimme("oneCozinhaModel");
		fourCozinhas = Fixture.from(Cozinha.class).gimme(4, "anyCozinha");
	}

}
