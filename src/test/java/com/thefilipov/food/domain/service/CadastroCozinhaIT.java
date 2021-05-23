package com.thefilipov.food.domain.service;

import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.exception.EntidadeNaoEncontradaException;
import com.thefilipov.food.domain.model.Cozinha;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @LocalServerPort
    private int port;

    /**
     * RestAssured - API Test
     */
    @Test
    @DisplayName("Retornar Status 200 - Quando Consultar Cozinhas")
    public void shouldRetornarStatus200_QuandoConsultarCozinhas() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        given()
            .basePath("/foodapi/cozinhas")
            .port(port)
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }


    /**
     * Teste Integrado
     */
    @Test
    @DisplayName("Quando Cadastrar Cozinha com dados corretos - Deve ser atribuído um Id")
    public void whenCadastroCozinhaComDadosCorretos_ThenDeveAtribuirId() {
        // cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        // ação
        novaCozinha = cozinhaService.salvar(novaCozinha);

        // validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
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
