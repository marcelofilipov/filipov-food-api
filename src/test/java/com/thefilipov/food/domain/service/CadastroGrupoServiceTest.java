package com.thefilipov.food.domain.service;

import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.model.Grupo;
import com.thefilipov.food.domain.repository.GrupoRepository;
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

@DisplayName("Teste Unitário da class CadastroGrupoService")
public class CadastroGrupoServiceTest extends ApplicationConfigTest {

	private static final long ID = 1L;
	private static final String NAME = "Administrador";

	@InjectMocks
	private CadastroGrupoService service;

	@Mock
	private GrupoRepository repository;

	private Grupo grupo;
	private Optional<Grupo> optionalGrupo;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startGrupo();
	}
	
	@Test
	@DisplayName("Buscar um Grupo")
	void shouldBuscarUmGrupo() {
		when(repository.findById(anyLong())).thenReturn(optionalGrupo);
		
		Grupo response = service.buscarOuFalhar(ID);
		
		assertNotNull(response);
		assertEquals(Grupo.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getNome());
	}
	
	private void startGrupo() {
		grupo = new Grupo(ID, NAME);
		optionalGrupo = Optional.of(new Grupo(ID, NAME));
	}

}
