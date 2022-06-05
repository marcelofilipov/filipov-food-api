package com.thefilipov.food.domain.service;

import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.repository.CozinhaRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DisplayName("CadastroCozinhaServiceTest")
public class CadastroCozinhaServiceTest extends ApplicationConfigTest {

	@MockBean
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cozinhaService;

	@Test
	@DisplayName("Buscar uma Cozinha")
	public void deveBuscarUmaCozinha() {
		Long cozinhaId = null;
		
		Optional<Cozinha> cozinha = Optional.of(criarCozinha());
		Mockito.when(cozinhaRepository.findById(ArgumentMatchers.eq(cozinhaId))).thenReturn(cozinha); 
		
		cozinhaService.buscarOuFalhar(cozinhaId);
	}
	
    private Cozinha criarCozinha() {
    	Cozinha cozinha = Mockito.mock(Cozinha.class);
    	Mockito.when(cozinha.getId()).thenReturn(0L);
    	Mockito.when(cozinha.getNome()).thenReturn("");
    	return cozinha;
	}

	@Test
    void hello() {
        CozinhaRepository mock = Mockito.mock(CozinhaRepository.class);
        List<Cozinha> findAll = mock.findAll();
        assertTrue(findAll.isEmpty());
    }
}
