package com.thefilipov.food.domain.service;

import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.exception.EstadoNaoEncontradoException;
import com.thefilipov.food.domain.model.Estado;
import com.thefilipov.food.domain.repository.EstadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@DisplayName("Teste Unitário da class CadastroEstadoService")
public class CadastroEstadoServiceTest extends ApplicationConfigTest {

	private static final long ID = 1L;
	private static final String NAME = "São Paulo";

	@InjectMocks
	private CadastroEstadoService service;

	@Mock
	private EstadoRepository repository;

	private Estado estado;
	private Optional<Estado> optionalEstado;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startEstado();
	}
	
	@Test
	@DisplayName("Buscar um Estado da Federação do Brasil")
	void deveBuscarUmEstado() {
		when(repository.findById(anyLong())).thenReturn(optionalEstado);
		
		Estado response = service.buscarOuFalhar(ID);
		
		assertNotNull(response);
		assertEquals(Estado.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getNome());
	}
	
	@Test
	@DisplayName("Retorna uma exceção quando não encontrar Estado da Federação do Brasil")
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(anyLong())).thenThrow(new EstadoNaoEncontradoException(ID));
		try {
			service.buscarOuFalhar(ID);
		} catch (Exception ex) {
			assertEquals(EstadoNaoEncontradoException.class, ex.getClass());
			assertEquals("Não existe um Estado cadastrado com o código 1", ex.getMessage());
		}
	}
	
	private void startEstado() {
		estado = new Estado(ID, NAME);
		optionalEstado = Optional.of(new Estado(ID, NAME));
	}

}
