package com.thefilipov.food.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.thefilipov.food.domain.exception.EntidadeNaoEncontradaException;
import com.thefilipov.food.domain.model.Cozinha;

@SpringBootTest
public class CadastroCozinhaServiceIT {

    private static final long COZINHA_ID_INEXISTENTE = 100L;

    /**
     * Teste Integrado
     */
    
    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Test
    @DisplayName("Quando Cadastrar Cozinha com dados corretos - Deve ser atribuído um Id")
    public void whenCadastroCozinhaComDadosCorretos_thenDeveAtribuirId() {
        // cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Romena");

        // ação
        novaCozinha = cozinhaService.salvar(novaCozinha);

        // validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve Falhar - Quando tentar Cadastrar Cozinha sem nome (NULL)")
    public void shouldFail_whenCadastrarCozinhaSemNome() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            Cozinha novaCozinha = new Cozinha();
            novaCozinha.setNome(null);
            novaCozinha = cozinhaService.salvar(novaCozinha);
        });
    }

    @Test
    @DisplayName("Falhar quando tentar Excluir uma Cozinha Inexistente")
    public void shouldFail_whenExcluirCozinhaInexistente() {
        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            cozinhaService.excluir(COZINHA_ID_INEXISTENTE);
        });
    }

}
