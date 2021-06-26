package com.thefilipov.food.domain.service;

import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.exception.EntidadeNaoEncontradaException;
import com.thefilipov.food.domain.model.Cozinha;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

    /**
     * RestAssured - API Test
     */
    @LocalServerPort
    private int port;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/foodapi/cozinhas";

        flyway.migrate();
    }

    @Test
    @DisplayName("Retornar Status 200 - Quando consultar cozinhas")
    public void shouldRetornarStatus200_whenConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deve conter 5 Cozinhas - Quando Consultar Cozinhas")
    public void shouldConter5Cozinhas_whenConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", hasSize(4))
            .body("nome", hasItems("Indiana", "Brasileira"));
    }

    @Test
    @DisplayName("Retornar Status 201 - Quando cadastrar uma cozinha")
    public void shouldRetornarStatus201_whenCadastrarCozinha() {
        given()
            .body("{ \"nome\": \"Colombiana\" }")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }


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
        assertThrows(ConstraintViolationException.class, () -> {
            Cozinha novaCozinha = new Cozinha();
            novaCozinha.setNome(null);
            novaCozinha = cozinhaService.salvar(novaCozinha);
        });
    }

    @Test
    @DisplayName("Falhar quando tentar Excluir um Cozinha em uso")
    public void shouldFalhar_whenExcluirCozinhaEmUso() {
        assertThrows(EntidadeEmUsoException.class, () -> {
            cozinhaService.excluir(1L);
        });
    }

    @Test
    @DisplayName("Falhar quando tentar excluir um Cozinha Inexistente")
    public void shouldFalhar_whenExcluirCozinhaInexistente() {
        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            cozinhaService.excluir(100L);
        });
    }

}
