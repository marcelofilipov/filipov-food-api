package com.thefilipov.food.domain.service;

import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.exception.EstadoNaoEncontradoException;
import com.thefilipov.food.domain.model.Estado;
import com.thefilipov.food.domain.repository.EstadoRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("Teste Unitário da class CadastroEstadoService")
class CadastroEstadoServiceTest extends ApplicationConfigTest {

	private static final long ID = 1L;

	@InjectMocks
	private CadastroEstadoService service;

	@Spy
	private EstadoRepository repository;

	private Estado estado;
	private Optional<Estado> oEstado;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startEstado();
	}
	
	@Test
	@DisplayName("Buscar um Estado da Federação do Brasil")
	void whenFindOneEstado() {
		when(repository.findById(anyLong())).thenReturn(oEstado);
		
		Estado response = service.buscarOuFalhar(ID);

		assertAll(() -> assertNotNull(response),
			() -> assertEquals(Estado.class, response.getClass()),
			() -> assertEquals(oEstado.get().getId(), response.getId()),
			() -> assertEquals(oEstado.get().getNome(), response.getNome())
		);
	}
	
	@Test
	@DisplayName("Deve lançar EstadoNaoEncontradoException ao tentar excluir um Estado não encontrado")
	void throwEstadoNaoEncontradoExceptionWhenTryingToDeleteEstadoThatNotExist() {
		when(repository.findById(anyLong())).thenThrow(new EstadoNaoEncontradoException(ID));
		try {
			service.buscarOuFalhar(ID);
		} catch (Exception ex) {
			assertEquals(EstadoNaoEncontradoException.class, ex.getClass());
			assertEquals("Não existe um Estado cadastrado com o código 1", ex.getMessage());
		}
	}

	@Test
	@DisplayName("Deve lançar EmptyResultDataAccessException ao tentar excluir um Estado não encontrado")
	void throwEmptyResultDataAccessExceptionWhenTryingToDeleteEstadoThatNotExist() {
		when(repository.findById(anyLong())).thenReturn(oEstado);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(ID);

		assertThrows(EstadoNaoEncontradoException.class, () -> service.excluir(ID));

		verify(repository, times(1)).deleteById(ID);
	}

	@Test
	@DisplayName("Deve lançar EntidadeEmUsoException ao tentar excluir um Estado em uso")
	void throwEntidadeEmUsoExceptionWhenTryingToDeleteEstadoInUse() {
		when(repository.findById(anyLong())).thenReturn(oEstado);
		doThrow(EntidadeEmUsoException.class).when(repository).deleteById(ID);

		assertThrows(EntidadeEmUsoException.class, () -> service.excluir(ID));

		verify(repository, times(1)).deleteById(ID);
	}

	@Test
	@DisplayName("Deve excluir um Estado com sucesso")
	void whenDeleteEstadoWithSuccess() {
		when(repository.findById(anyLong())).thenReturn(oEstado);

		service.excluir(ID);

		verify(repository, times(1)).deleteById(ID);
	}

	private void startEstado() {
		Faker faker = new Faker(new Locale("pt-BR"));
		final String name = faker.address().state();

		estado = new Estado(ID, name);
		oEstado = Optional.of(new Estado(ID, name));
	}

}
