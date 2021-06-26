package com.thefilipov.food.domain.service;

import com.thefilipov.food.domain.exception.EntidadeNaoEncontradaException;
import com.thefilipov.food.domain.model.Estado;
import com.thefilipov.food.domain.repository.EstadoRepository;
import com.thefilipov.food.util.DatabaseCleaner;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroEstadoIT {

    /**
     * RestAssured - API Test
     */
    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private EstadoRepository estadoRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/foodapi/estados";

        databaseCleaner.clearTables();
        preparingData();
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
    @DisplayName("Retornar uma resposta e Status 200 - Quando consultar um estado existente")
    public void shouldRetornarUmaRespostaEStatus200_whenConsultarEstadoExistente() {
        given()
            .pathParam("estadoId", 3)
            .accept(ContentType.JSON)
        .when()
            .get("/{estadoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo("Paraná"));
    }

    @Test
    @DisplayName("Retornar Status 404 - Quando consultar um estado inexistente")
    public void shouldRetornarStatus404_whenConsultarEstadoInexistente() {
        given()
            .pathParam("estadoId", 100)
            .accept(ContentType.JSON)
        .when()
            .get("/{estadoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve conter 4 Estados - Quando Consultar Estados")
    public void shouldConter5Estados_whenConsultarEstados() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", hasSize(4))
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

    @Test
    @DisplayName("Deve Falhar - Quando tentar Cadastrar Estado sem nome (NULL)")
    public void shouldFail_whenCadastrarEstadoSemNome() {
        assertThrows(ConstraintViolationException.class, () -> {
            Estado novoEstado = new Estado();
            novoEstado.setNome(null);
            novoEstado = estadoService.salvar(novoEstado);
        });
    }

    /*
    @Test
    @DisplayName("Falhar quando tentar Excluir um Estado Em Uso")
    public void shouldFail_whenExcluirCozinhaEmUso() {
        assertThrows(EntidadeEmUsoException.class, () -> {
            estadoService.excluir(1L);
        });
    }
    */

    @Test
    @DisplayName("Falhar quando tentar Excluir um Estado Inexistente")
    public void shouldFail_whenExcluirCozinhaInexistente() {
        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            estadoService.excluir(100L);
        });
    }

    private void preparingData() {
        Estado estado1 = new Estado();
        estado1.setNome("São Paulo");
        estadoRepository.save(estado1);

        Estado estado2 = new Estado();
        estado2.setNome("Minas Gerais");
        estadoRepository.save(estado2);

        Estado estado3 = new Estado();
        estado3.setNome("Paraná");
        estadoRepository.save(estado3);

        Estado estado4 = new Estado();
        estado4.setNome("Amazonas");
        estadoRepository.save(estado4);
    }
}
