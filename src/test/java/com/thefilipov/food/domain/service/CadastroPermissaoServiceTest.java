package com.thefilipov.food.domain.service;

import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.model.Permissao;
import com.thefilipov.food.domain.repository.PermissaoRepository;
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

public class CadastroPermissaoServiceTest extends ApplicationConfigTest {

    private static final long ID = 1L;
    private static final String NAME = "CONSULTAR_COZINHAS";
    private static final String DESCRIPTION = "Permite consultar cozinhas";

    @InjectMocks
    private CadastroPermissaoService service;

    @Mock
    private PermissaoRepository repository;

    private Permissao permissao;
    private Optional<Permissao> optionalPermissao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPermissao();
    }

    @Test
    @DisplayName("Buscar uma Permissão")
    void deveBuscarUmaPermissao() {
        when(repository.findById(anyLong())).thenReturn(optionalPermissao);

        Permissao response = service.buscarOuFalhar(ID);

        assertNotNull(response);
        assertEquals(Permissao.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getNome());
        assertEquals(DESCRIPTION, response.getDescricao());
    }

    private void startPermissao() {
        permissao = new Permissao(ID, NAME, DESCRIPTION);
        optionalPermissao = Optional.of(new Permissao(ID, NAME, DESCRIPTION));
    }
}