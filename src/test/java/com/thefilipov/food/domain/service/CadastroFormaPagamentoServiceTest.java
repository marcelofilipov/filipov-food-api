package com.thefilipov.food.domain.service;

import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.model.FormaPagamento;
import com.thefilipov.food.domain.repository.FormaPagamentoRepository;
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
	void deveBuscarUmaFormaDePagto() {
		when(repository.findById(anyLong())).thenReturn(optionalFormaPagto);
		
		FormaPagamento response = service.buscarOuFalhar(ID);
		
		assertNotNull(response);
		assertEquals(FormaPagamento.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(DESCRIPTION, response.getDescricao());
	}
	
	private void startFormaPagto() {
		formaPagto = new FormaPagamento(ID, DESCRIPTION);
		optionalFormaPagto = Optional.of(new FormaPagamento(ID, DESCRIPTION));
	}

}
