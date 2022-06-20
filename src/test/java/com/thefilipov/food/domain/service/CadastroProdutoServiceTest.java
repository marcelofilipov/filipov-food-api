package com.thefilipov.food.domain.service;

import com.thefilipov.food.ApplicationConfigTest;
import com.thefilipov.food.domain.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste Unitário da class CadastroProdutoServiceTest")
class CadastroProdutoServiceTest extends ApplicationConfigTest {

    @InjectMocks
    private CadastroProdutoService service;

    @Mock
    private ProdutoRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvar() {
    }

    @Test
    void buscarOuFalhar() {
    }
}