package com.thefilipov.food.domain.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.exception.GrupoNaoEncontradoException;
import com.thefilipov.food.domain.model.Grupo;
import com.thefilipov.food.domain.repository.GrupoRepository;
import com.thefilipov.food.templates.GrupoTemplates;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("Teste Unitário da class CadastroGrupoService")
public class CadastroGrupoServiceTest extends ApplicationConfigTest {

	private static final long ID = 1L;
	private static final long NONEXISTENT = 100L;
	private static final String NAME = "Gerente";
	private static final String GRUPO_NAO_ENCONTRADO = "Não existe um Grupo cadastrado com o código 1";

	@InjectMocks
	private CadastroGrupoService service;

	@Mock
	private GrupoRepository repository;

	private Grupo saveGrupo;
	private Optional<Grupo> oneGrupo;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		FixtureFactoryLoader.loadTemplates(GrupoTemplates.class.getPackage().getName());
		startGrupo();
	}
	
	@Test
	@DisplayName("Buscar um Grupo")
	void whenFindByIdThenReturnGrupoInstance() {
		when(repository.findById(anyLong())).thenReturn(oneGrupo);

		Grupo response = service.buscarOuFalhar(ID);

		assertAll(() -> assertNotNull(response),
			() -> assertEquals(Grupo.class, response.getClass()),
			() -> assertEquals(ID, response.getId()),
			() -> assertEquals(NAME, response.getNome())
		);
	}
	
	@Test
	@DisplayName("Retorna uma exceção quando não encontrar Grupo")
	void whenFindByIdThenReturnGrupoNaoEncontradoException() {
		when(repository.findById(anyLong())).thenThrow(new GrupoNaoEncontradoException(ID));
		
		try {
			service.buscarOuFalhar(ID);
		} catch (Exception ex) {
			assertAll(
				() -> assertEquals(GrupoNaoEncontradoException.class, ex.getClass()),
				() -> assertEquals(GRUPO_NAO_ENCONTRADO, ex.getMessage())
			);
		}
	}

	@RepeatedTest(value = 3)
	@DisplayName("Insere um Grupo")
	void whenCreateThenReturnSuccess() {
		when(repository.save(any())).thenReturn(saveGrupo);

		Grupo response = service.salvar(saveGrupo);

		assertNotNull(response);
		assertEquals(Grupo.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getNome());
	}

	@Test
	@DisplayName("Exclui um Grupo com sucesso")
	void deleteWithSuccess() {
		when(repository.findById(anyLong())).thenReturn(oneGrupo);
		doNothing().when(repository).deleteById(anyLong());

		service.excluir(ID);

		verify(repository, times(1)).deleteById(anyLong());
	}

	@Test
	@DisplayName("Falhar quando tentar Excluir um Grupo Inexistente")
	void shouldFail_whenExcluirGrupoInexistente() {
		when(repository.findById(anyLong())).thenThrow(new GrupoNaoEncontradoException(ID));

		try {
			service.excluir(ID);
		} catch (Exception ex) {
			assertEquals(GrupoNaoEncontradoException.class, ex.getClass());
			assertEquals(GRUPO_NAO_ENCONTRADO, ex.getMessage());
		}
	}

	@Test
	void deleteWithEmptyResultDataAccessException() {
		when(repository.findById(anyLong())).thenThrow(new EmptyResultDataAccessException(1));

		try {
			service.excluir(NONEXISTENT);
		} catch (EmptyResultDataAccessException ex) {
			assertEquals(EmptyResultDataAccessException.class, ex.getClass());
			assertEquals(GRUPO_NAO_ENCONTRADO, ex.getMessage());
		}
	}

	@Test
	void deleteWithGrupoEmUsoException() {
		when(repository.findById(anyLong()))
				.thenThrow(new EntidadeEmUsoException(String.format(CadastroGrupoService.MSG_GRUPO_EM_USO, ID)));

		try {
			service.excluir(ID);
		} catch (Exception ex) {
			assertEquals(EntidadeEmUsoException.class, ex.getClass());
			assertEquals(String.format(CadastroGrupoService.MSG_GRUPO_EM_USO, ID), ex.getMessage());
		}
	}

	private void startGrupo() {
		saveGrupo = Fixture.from(Grupo.class).gimme("oneGrupo");
		oneGrupo = Optional.of(Fixture.from(Grupo.class).gimme("oneGrupo"));
	}

}
