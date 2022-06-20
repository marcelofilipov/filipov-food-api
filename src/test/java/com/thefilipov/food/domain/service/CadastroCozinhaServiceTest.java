package com.thefilipov.food.domain.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.exception.CozinhaNaoEncontradaException;
import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.repository.CozinhaRepository;
import com.thefilipov.food.templates.CozinhaTemplates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("Teste Unitário da class CadastroCozinhaService")
public class CadastroCozinhaServiceTest extends ApplicationConfigTest {

	private static final long ID = 1L;
	private static final long NONEXISTENT = 100L;
	private static final String NAME = "Brasileira";

	private static final String COZINHA_NAO_ENCONTRADA = "Não existe uma Cozinha cadastrada com o código 1";
	
	@InjectMocks
	private CadastroCozinhaService service;

	@Mock
	private CozinhaRepository repository;

	private Cozinha saveCozinha;

	private Optional<Cozinha> oneCozinha;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		FixtureFactoryLoader.loadTemplates(CozinhaTemplates.class.getPackage().getName());
		startCozinha();
	}
	
	@Test
	@DisplayName("Buscar uma Cozinha")
	void whenFindByIdThenReturnCozinhaInstance() {
		when(repository.findById(anyLong())).thenReturn(oneCozinha);
		
		Cozinha response = service.buscarOuFalhar(ID);

		assertAll(() -> assertNotNull(response),
			() -> assertEquals(Cozinha.class, response.getClass()),
			() -> assertEquals(ID, response.getId()),
			() -> assertEquals(NAME, response.getNome())
		);
	}

	@Test
	@DisplayName("Retorna uma exceção quando não encontrar Cozinha")
	void whenFindByIdThenReturnCozinhaNaoEncontradaException() {
		when(repository.findById(anyLong())).thenThrow(new CozinhaNaoEncontradaException(ID));

		try {
			service.buscarOuFalhar(ID);
		} catch (Exception ex) {
			assertAll(
				() -> assertEquals(CozinhaNaoEncontradaException.class, ex.getClass()),
				() -> assertEquals(COZINHA_NAO_ENCONTRADA, ex.getMessage())
			);
		}
	}

	@RepeatedTest(value = 3)
	@DisplayName("Insere uma Cozinha")
	void whenCreateThenReturnSuccess() {
		when(repository.save(any())).thenReturn(saveCozinha);
		
		Cozinha response = service.salvar(saveCozinha);
		
		assertNotNull(response);
		assertEquals(Cozinha.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getNome());
	}
	
	@Test
	@DisplayName("Exclui uma Cozinha com sucesso")
	void deleteWithSuccess() {
		when(repository.findById(anyLong())).thenReturn(oneCozinha);
		doNothing().when(repository).deleteById(anyLong());
		
		service.excluir(ID);
		
		verify(repository, times(1)).deleteById(anyLong());
	}
	
	@Test
	@DisplayName("Falhar quando tentar Excluir uma Cozinha Inexistente")
	void shouldFail_whenExcluirCozinhaInexistente() {
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
			service.excluir(NONEXISTENT);
		} catch (EmptyResultDataAccessException ex) {
			assertEquals(EmptyResultDataAccessException.class, ex.getClass());
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
		saveCozinha = Fixture.from(Cozinha.class).gimme("oneCozinha");
		oneCozinha = Optional.of(Fixture.from(Cozinha.class).gimme("oneCozinha"));
	}

}
