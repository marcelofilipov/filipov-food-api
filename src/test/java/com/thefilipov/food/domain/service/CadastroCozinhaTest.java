package com.thefilipov.food.domain.service;

import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.repository.CozinhaRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class CadastroCozinhaTest {

    @Test
    void hello() {
        CozinhaRepository mock = Mockito.mock(CozinhaRepository.class);
        List<Cozinha> findAll = mock.findAll();
        assertTrue(findAll.isEmpty());
    }
}
