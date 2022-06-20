package com.thefilipov.food.api.exceptionhandler;

import com.thefilipov.food.ApplicationConfigTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class ApiExceptionHandlerTest extends ApplicationConfigTest {

    @InjectMocks
    private ApiExceptionHandler apiExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleUncaught() {
    }

    @Test
    void whenHandleEntidadeNaoEncontradoExceptionThenReturnAResponseEntity() {
    }

    @Test
    void handleEntidadeEmUsoException() {
    }

    @Test
    void handleNegocioExceptionException() {
    }

    @Test
    void handleValidacaoException() {
    }

    @Test
    void handleExceptionInternal() {
    }

    @Test
    void handleHttpMessageNotReadable() {
    }

    @Test
    void handleTypeMismatch() {
    }

    @Test
    void handleNoHandlerFoundException() {
    }

    @Test
    void handleMethodArgumentNotValid() {
    }
}