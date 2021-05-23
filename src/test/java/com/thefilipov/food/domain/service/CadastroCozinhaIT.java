package com.thefilipov.food.domain.service;

import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.exception.EntidadeNaoEncontradaException;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.service.CadastroCozinhaService;
import org.assertj.core.api.Assertions;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CadastroCozinhaIT {

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Test
    public void whenCadastroCozinhaComDadosCorretos_ThenDeveAtribuirId() {
        // cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        // ação
        novaCozinha = cozinhaService.salvar(novaCozinha);

        // validação
        Assertions.assertThat(novaCozinha).isNotNull();
        Assertions.assertThat(novaCozinha.getId()).isNotNull();
    }

    /*
    @Test
    public void shouldFalhar_whenCadastrarCozinhaSemNome() {
        assertThrows(ConstraintViolationException.class, () -> {
            Cozinha novaCozinha = new Cozinha();
            novaCozinha.setNome(null);
            novaCozinha = cozinhaService.salvar(novaCozinha);
        });
    }
    */

    @Test
    public void shouldFalhar_whenExcluirCozinhaEmUso() {
        assertThrows(EntidadeEmUsoException.class, () -> {
            cozinhaService.excluir(1L);
        });
    }

    @Test
    public void shouldFalhar_whenExcluirCozinhaInexistente() {
        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            cozinhaService.excluir(100L);
        });
    }

}
