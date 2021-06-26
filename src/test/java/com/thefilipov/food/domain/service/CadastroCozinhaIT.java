package com.thefilipov.food.domain.service;

import com.thefilipov.food.domain.exception.EntidadeNaoEncontradaException;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.repository.CozinhaRepository;
import com.thefilipov.food.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
import static org.hamcrest.Matchers.*;
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
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/foodapi/cozinhas";

        databaseCleaner.clearTables();
        preparingData();
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
    @DisplayName("Retornar uma resposta e Status 200 - Quando consultar uma cozinha existente")
    public void shouldRetornarUmaRespostaEStatus200_whenConsultarCozinhaExistente() {
        given()
            .pathParam("cozinhaId", 3)
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo("Brasileira"));
    }

    @Test
    @DisplayName("Retornar Status 404 - Quando consultar uma cozinha inexistente")
    public void shouldRetornarStatus404_whenConsultarCozinhaInexistente() {
        given()
            .pathParam("cozinhaId", 100)
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve conter 3 Cozinhas - Quando Consultar Cozinhas")
    public void shouldConter3Cozinhas_whenConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", hasSize(3))
            .body("nome", hasItems("Americana", "Brasileira"));
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

    /*
    @Test
    @DisplayName("Falhar quando tentar Excluir uma Cozinha Em Uso")
    public void shouldFail_whenExcluirCozinhaEmUso() {
        assertThrows(EntidadeEmUsoException.class, () -> {
            cozinhaService.excluir(1L);
        });
    }
    */

    @Test
    @DisplayName("Falhar quando tentar Excluir uma Cozinha Inexistente")
    public void shouldFail_whenExcluirCozinhaInexistente() {
        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            cozinhaService.excluir(100L);
        });
    }

    private void preparingData() {
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");
        cozinhaRepository.save(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Americana");
        cozinhaRepository.save(cozinha2);

        Cozinha cozinha3 = new Cozinha();
        cozinha3.setNome("Brasileira");
        cozinhaRepository.save(cozinha3);
    }
}
