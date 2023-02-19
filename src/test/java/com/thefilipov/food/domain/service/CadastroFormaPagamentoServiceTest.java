package com.thefilipov.food.domain.service;

import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.exception.FormaPagamentoNaoEncontradaException;
import com.thefilipov.food.domain.model.FormaPagamento;
import com.thefilipov.food.domain.repository.FormaPagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("Teste Unitário da class CadastroFormatPagamentoService")
public class CadastroFormaPagamentoServiceTest extends ApplicationConfigTest {

	private static final long ID = 1L;
	private static final String DESCRIPTION = "Dinheiro";

	@InjectMocks
	private CadastroFormaPagamentoService service;

	@Mock
	private FormaPagamentoRepository repository;

	private FormaPagamento formaPagto;
	private Optional<FormaPagamento> optionalFormaPagto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startFormaPagto();
	}
	
	@Test
	@DisplayName("Buscar uma Forma de Pagamento")
	void whenFindOneFormaDePagto() {
		when(repository.findById(anyLong())).thenReturn(optionalFormaPagto);
		
		FormaPagamento response = service.buscarOuFalhar(ID);
		
		assertNotNull(response);
		assertEquals(FormaPagamento.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(DESCRIPTION, response.getDescricao());
	}
	@Test
	@DisplayName("Deve lançar FormaPagamentoNaoEncontradaException ao tentar excluir uma Forma Pagto não encontrada")
	void throwFormaPagamentoNaoEncontradaExceptionWhenTryingToDeleteFormaDePagtoThatNotExist() {
		when(repository.findById(anyLong())).thenThrow(new FormaPagamentoNaoEncontradaException(ID));
		try {
			service.buscarOuFalhar(ID);
		} catch (Exception ex) {
			assertEquals(FormaPagamentoNaoEncontradaException.class, ex.getClass());
			assertEquals("Não existe um cadastro de forma de pagamento com código 1", ex.getMessage());
		}
	}

	@Test
	@DisplayName("Deve lançar EntidadeEmUsoException ao tentar excluir uma Forma Pagto em uso")
	void throwEntidadeEmUsoExceptionWhenTryingToDeleteFormaPagtoInUse() {
		when(repository.findById(anyLong())).thenReturn(optionalFormaPagto);
		doThrow(EntidadeEmUsoException.class).when(repository).deleteById(1L);

		assertThrows(EntidadeEmUsoException.class, () -> service.excluir(1L));

		verify(repository, times(1)).deleteById(1L);
	}

	@Test
	@DisplayName("Deve excluir uma Forma Pagto com sucesso")
	void whenDeleteFormaPagtoWithSuccess() {
		when(repository.findById(1L)).thenReturn(optionalFormaPagto);

		service.excluir(1L);

		verify(repository, times(1)).deleteById(1L);
	}


	private void startFormaPagto() {
		formaPagto = new FormaPagamento(ID, DESCRIPTION);
		optionalFormaPagto = Optional.of(new FormaPagamento(ID, DESCRIPTION));
	}

}
