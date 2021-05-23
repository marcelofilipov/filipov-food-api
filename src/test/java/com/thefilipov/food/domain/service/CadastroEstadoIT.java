package com.thefilipov.food.domain.service;

import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.exception.EntidadeNaoEncontradaException;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.model.Estado;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroEstadoIT {

    /**
     * RestAssured - API Test
     */
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/foodapi/estados";
    }

    @Test
    @DisplayName("Retornar Status 200 - Quando consultar estados")
    public void shouldRetornarStatus200_whenConsultarEstados() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deve conter 5 Estados - Quando Consultar Estados")
    public void shouldConter5Estados_whenConsultarEstados() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", hasSize(5))
            .body("nome", hasItems("São Paulo", "Amazonas"));
    }

    @Test
    @DisplayName("Retornar Status 201 - Quando cadastrar um estado")
    public void shouldRetornarStatus201_whenCadastrarCozinha() {
        given()
            .body("{ \"nome\": \"Acre\" }")
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
    private CadastroEstadoService estadoService;

    @Test
    @DisplayName("Quando Cadastrar Estado com dados corretos - Deve ser atribuído um Id")
    public void whenCadastroEstadoComDadosCorretos_ThenDeveAtribuirId() {
        Estado novoEstado = new Estado();
        novoEstado.setNome("Rio Grande do Sul");

        novoEstado = estadoService.salvar(novoEstado);

        Assertions.assertThat(novoEstado).isNotNull();
        Assertions.assertThat(novoEstado.getId()).isNotNull();
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
    @DisplayName("Falhar quando tentar Excluir um Estado em uso")
    public void shouldFalhar_whenExcluirCozinhaEmUso() {
        assertThrows(EntidadeEmUsoException.class, () -> {
            estadoService.excluir(1L);
        });
    }

    @Test
    @DisplayName("Falhar quando tentar excluir um Estado Inexistente")
    public void shouldFalhar_whenExcluirCozinhaInexistente() {
        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            estadoService.excluir(100L);
        });
    }

}
