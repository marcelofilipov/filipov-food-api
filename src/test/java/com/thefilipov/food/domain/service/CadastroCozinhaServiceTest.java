package com.thefilipov.food.domain.service;

import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.api.model.CozinhaModel;
import com.thefilipov.food.domain.exception.CozinhaNaoEncontradaException;
import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.repository.CozinhaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("Teste Unitário da class CadastroCozinhaService")
public class CadastroCozinhaServiceTest extends ApplicationConfigTest {

	private static final long ID = 1L;
	private static final String NAME = "Brasileira";

	private static final String COZINHA_NAO_ENCONTRADA = "Não existe uma Cozinha cadastrada com o código 1";
	
	@InjectMocks
	private CadastroCozinhaService service;

	@Mock
	private CozinhaRepository repository;

	private Cozinha cozinha;
	private CozinhaModel cozinhaModel;
	private Optional<Cozinha> optionalCozinha;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startCozinha();
	}
	
	@Test
	@DisplayName("Buscar uma Cozinha")
	void whenFindByIdThenReturnCozinhaInstance() {
		when(repository.findById(anyLong())).thenReturn(optionalCozinha);
		
		Cozinha response = service.buscarOuFalhar(ID);
		
		assertNotNull(response);
		assertEquals(Cozinha.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getNome());
	}

	@Test
	@DisplayName("Retorna uma exceção quando não encontrar Cozinha")
	void whenFindByIdThenReturnCozinhaNaoEncontradaException() {
		when(repository.findById(anyLong())).thenThrow(new CozinhaNaoEncontradaException(ID));
		
		try {
			service.buscarOuFalhar(ID);
		} catch (Exception ex) {
			assertEquals(CozinhaNaoEncontradaException.class, ex.getClass());
			assertEquals(COZINHA_NAO_ENCONTRADA, ex.getMessage());
		}
	}
	
	@Test
	@DisplayName("Insere uma Cozinha")
	void whenCreateThenReturnSucess() {
		when(repository.save(Mockito.any())).thenReturn(cozinha);
		
		Cozinha response = service.salvar(cozinha);
		
		assertNotNull(response);
		assertEquals(Cozinha.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getNome());
	}
	
	@Test
	@DisplayName("Exclui uma Cozinha com sucesso")
	void deleteWithSucess() {
		when(repository.findById(anyLong())).thenReturn(optionalCozinha);
		doNothing().when(repository).deleteById(anyLong());
		
		service.excluir(ID);
		
		verify(repository, times(1)).deleteById(anyLong());
	}
	
	@Test
	void deleteWithCozinhaNaoEncontradaException() {
		when(repository.findById(anyLong())).thenThrow(new CozinhaNaoEncontradaException(ID));
		
		try {
			service.excluir(ID);
		} catch (Exception ex) {
			assertEquals(CozinhaNaoEncontradaException.class, ex.getClass());
			assertEquals(COZINHA_NAO_ENCONTRADA, ex.getMessage());
		}
	}

	@Test
	void deleteWithEmptyResultDataAccessException() {
		when(repository.findById(anyLong())).thenThrow(new EmptyResultDataAccessException(1));
		
		try {
			service.excluir(ID);
		} catch (EmptyResultDataAccessException ex) {
			assertEquals(CozinhaNaoEncontradaException.class, ex.getClass());
			assertEquals(COZINHA_NAO_ENCONTRADA, ex.getMessage());
		}
	}

	@Test
	void deleteWithCozinhaEmUsoException() {
		when(repository.findById(anyLong()))
			.thenThrow(new EntidadeEmUsoException(String.format(CadastroCozinhaService.MSG_COZINHA_EM_USO, ID)));
		
		try {
			service.excluir(ID);
		} catch (Exception ex) {
			assertEquals(EntidadeEmUsoException.class, ex.getClass());
			assertEquals(String.format(CadastroCozinhaService.MSG_COZINHA_EM_USO, ID), ex.getMessage());
		}
	}
	
	private void startCozinha() {
		cozinha = new Cozinha(ID, NAME);
		cozinhaModel = new CozinhaModel(ID, NAME);
		optionalCozinha = Optional.of(new Cozinha(ID, NAME));
	}

}
