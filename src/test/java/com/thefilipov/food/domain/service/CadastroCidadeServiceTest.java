package com.thefilipov.food.domain.service;

import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.exception.CidadeNaoEncontradaException;
import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.model.Cidade;
import com.thefilipov.food.domain.model.Estado;
import com.thefilipov.food.domain.repository.CidadeRepository;
import com.thefilipov.food.domain.repository.EstadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("Teste Unitário da class CadastroCidadeService")
class CadastroCidadeServiceTest extends ApplicationConfigTest {

    private static final long ID = 1L;
    private static final String NAME = "Santo André";

    @InjectMocks
    private CadastroCidadeService service;

    @Mock
    private CadastroEstadoService cadastroEstado;

    @Mock
    private CidadeRepository repository;

    @Mock
    private EstadoRepository estadoRepository;

    @Mock
    private Cidade cidade;

    @Mock
    private Estado estado;

    @Captor
    private ArgumentCaptor<Cidade> cidadeCaptor;

    private Optional<Cidade> optionalCidade;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);
        startCidade();
    }

    @Test
    @DisplayName("Buscar uma Cidade do Estado")
    void whenFindOneCidade() {
        when(repository.findById(anyLong())).thenReturn(optionalCidade);

        Cidade response = service.buscarOuFalhar(ID);

        assertNotNull(response);
        assertEquals(Cidade.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getNome());
    }

    @Test
    @DisplayName("Deve lançar CidadeNaoEncontradaException ao tentar excluir uma Cidade não encontrada")
    void throwCidadeNaoEncontradaExceptionWhenTryingToDeleteCidadeThatNotExist() {
        when(repository.findById(anyLong())).thenThrow(new CidadeNaoEncontradaException(ID));
        try {
            service.buscarOuFalhar(ID);
        } catch (Exception ex) {
            assertEquals(CidadeNaoEncontradaException.class, ex.getClass());
            assertEquals("Não existe um cadastro de Cidade com o código 1", ex.getMessage());
        }
    }

    @Test
    @DisplayName("Deve lançar EntidadeEmUsoException ao tentar excluir uma Cidade em uso")
    void throwEntidadeEmUsoExceptionWhenTryingToDeleteEstadoInUse() {
        when(repository.findById(anyLong())).thenReturn(optionalCidade);
        doThrow(EntidadeEmUsoException.class).when(repository).deleteById(1L);

        assertThrows(EntidadeEmUsoException.class, () -> service.excluir(1L));

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve excluir uma Cidade com sucesso")
    void whenDeleteCidadeWithSuccess() {
        when(repository.findById(1L)).thenReturn(optionalCidade);

        service.excluir(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    private void startCidade() {
        cidade = new Cidade(ID, NAME);
        optionalCidade = Optional.of(new Cidade(ID, NAME));
    }

    @Test
    @DisplayName("Deve salvar uma Cidade com sucesso")
    void whenSaveCidadeWithEstadoExistentWithSuccess() {
        Estado estado = new Estado();
        estado.setId(1L);

        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.setEstado(estado);

        when(cadastroEstado.buscarOuFalhar(1L)).thenReturn(estado);
        when(repository.save(any(Cidade.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cidade cidadeSalva = service.salvar(cidade);

        assertNotNull(cidadeSalva);
        assertEquals("São Paulo", cidadeSalva.getNome());
        assertEquals(estado, cidadeSalva.getEstado());

        ArgumentCaptor<Cidade> captor = ArgumentCaptor.forClass(Cidade.class);
        verify(repository).save(captor.capture());
        assertEquals(estado, captor.getValue().getEstado());
    }

}