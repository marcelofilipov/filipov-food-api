package com.thefilipov.food.api.controller;

import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.repository.CozinhaRepository;
import com.thefilipov.food.util.DatabaseCleaner;
import com.thefilipov.food.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CozinhaControllerIT {

    /**
     * RestAssured - API Test
     */

    private static final long COZINHA_ID_INEXISTENTE = 100L;

    private Cozinha cozinhaAmericana;
    private int quantidadeCozinhasCadastradas;
    private String jsonCorretoCozinhaRussa;

    @Value("${local.server.port}")
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

        jsonCorretoCozinhaRussa = ResourceUtils.getContentFromResource("/json/ok/cozinha-russa.json");
    }
    
    @Test
    @DisplayName("Retornar Status 200 - Quando consultar cozinhas")
    void shouldRetornarStatus200_whenConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }
    
    @Test
    @DisplayName("Retornar uma resposta e Status 200 - Quando consultar uma cozinha existente")
    void shouldRetornarUmaRespostaEStatus200_whenConsultarCozinhaExistente() {
        given()
            .pathParam("cozinhaId", cozinhaAmericana.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(cozinhaAmericana.getNome()));
    }

    @Test
    @DisplayName("Retornar Status 404 - Quando consultar uma cozinha inexistente")
    void shouldRetornarStatus404_whenConsultarCozinhaInexistente() {
        given()
            .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve retornar status 200 e lista de cozinhas - Quando Consultar Cozinhas")
    void shouldRetornarQuantidadeCorretaDeCozinhas_whenConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("page.totalElements", is(quantidadeCozinhasCadastradas))
            .body("_embedded.cozinhas.nome", hasItems("Americana", "Brasileira"));
    }

    @Test
    @DisplayName("Retornar Status 201 - Quando cadastrar uma cozinha")
    void shouldRetornarStatus201_whenCadastrarCozinha() {
        given()
            .body(jsonCorretoCozinhaRussa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    private void preparingData() {
        Cozinha cozinhaTailandesa = new Cozinha();
        cozinhaTailandesa.setNome("Tailandesa");
        cozinhaRepository.save(cozinhaTailandesa);

        cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        this.quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
    }

}
