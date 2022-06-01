package com.thefilipov.food.domain.service;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.model.Restaurante;
import com.thefilipov.food.domain.repository.CozinhaRepository;
import com.thefilipov.food.domain.repository.RestauranteRepository;
import com.thefilipov.food.util.DatabaseCleaner;
import com.thefilipov.food.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

    /**
     * RestAssured - API Test
     */

    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
    private static final long RESTAURANTE_ID_INEXISTENTE = 100L;

    private Restaurante burgerTopRestaurante;
    private int quantidadeRestaurantesCadastrados;
    private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/foodapi/restaurantes";

        jsonRestauranteCorreto = ResourceUtils.getContentFromResource(
                "/json/ok/restaurante-new-york-barbecue.json");

        jsonRestauranteSemFrete = ResourceUtils.getContentFromResource(
                "/json/fail/restaurante-new-york-barbecue-sem-frete.json");

        jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource(
                "/json/fail/restaurante-new-york-barbecue-sem-cozinha.json");

        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource(
                "/json/fail/restaurante-new-york-barbecue-com-cozinha-inexistente.json");

        databaseCleaner.clearTables();
        preparingData();
    }

    @Test
    @DisplayName("Retornar Status 200 - Quando consultar Restaurantes")
    public void shouldRetornarStatus200_whenConsultarRestaurantes() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Retornar Status 201 - Quando cadastrar um Restaurante")
    public void shouldRetornarStatus201_whenCadastrarRestaurante() {
        given()
            .body(jsonRestauranteCorreto)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Retornar Status 400 - Quando tenta cadastrar um Restaurante Sem Taxa Frete")
    public void shouldRetornarStatus400_whenCadastrarRestauranteSemTaxaFrete() {
        given()
            .body(jsonRestauranteSemFrete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    @DisplayName("Retornar Status 400 - Quando tenta cadastrar um Restaurante Sem Cozinha")
    public void shouldRetornarStatus400_whenCadastrarRestauranteSemCozinha() {
        given()
            .body(jsonRestauranteSemCozinha)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    @DisplayName("Retornar Status 400 - Quando tenta cadastrar um Restaurante Com Cozinha Inexistente")
    public void shouldRetornarStatus400_whenCadastrarRestauranteComCozinhaInexistente() {
        given()
            .body(jsonRestauranteComCozinhaInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }

    @Test
    @DisplayName("Retornar uma resposta e Status 200 - Quando consultar um restaurante existente")
    public void shouldRetornarRespostaEStatusCorretos_whenConsultarRestauranteExistente() {
        given()
            .pathParam("restauranteId", burgerTopRestaurante.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(burgerTopRestaurante.getNome()));
    }

    @Test
    @DisplayName("Retornar Status 404 - Quando consultar um restaurante inexistente")
    public void shouldRetornarStatus404_whenConsultarRestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    @DisplayName("Retornar Status 204 - Quando ativar um restaurante existente")
    public void shouldRetornarStatus204_whenAtivarRestauranteExistente() {
        given()
            .pathParam("restauranteId", burgerTopRestaurante.getId())
            .accept(ContentType.JSON)
        .when()
            .put("/{restauranteId}/ativo")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Retornar Status 204 - Quando inativar um restaurante existente")
    public void shouldRetornarStatus204_whenInativarRestauranteExistente() {
        given()
            .pathParam("restauranteId", burgerTopRestaurante.getId())
            .accept(ContentType.JSON)
        .when()
            .delete("/{restauranteId}/ativo")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }


    /**
     * Testes Integrados
     */
    
    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Autowired
    private CadastroRestauranteService restauranteService;

    @Test
    @DisplayName("Quando Cadastrar Restaurante com dados corretos - Deve ser atribuído um Id")
    public void whenCadastroRestauranteComDadosCorretos_thenDeveAtribuirId() {
        // cenário
        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setId(1L);
        cozinhaBrasileira.setNome("Brasileira");

        Restaurante novoRestaurante = new Restaurante();
        novoRestaurante.setNome("Restaurante Paulista");
        novoRestaurante.setTaxaFrete(BigDecimal.valueOf(10.0));
        novoRestaurante.setCozinha(cozinhaBrasileira);

        // ação
        novoRestaurante = restauranteService.salvar(novoRestaurante);

        // validação
        assertThat(novoRestaurante).isNotNull();
        assertThat(novoRestaurante.getId()).isNotNull();
    }

    @Test
    @DisplayName("Falhar quando tentar Excluir uma Cozinha Em Uso")
    public void shouldFail_whenExcluirCozinhaEmUso() {
        assertThrows(EntidadeEmUsoException.class, () -> {
            cozinhaService.excluir(2L);
        });
    }

    @Test
    @DisplayName("Deve Falhar - Quando tentar Cadastrar Restaurante sem nome (NULL)")
    public void shouldFail_whenCadastrarCozinhaSemNome() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            Cozinha cozinhaBrasileira = new Cozinha();
            cozinhaBrasileira.setId(1L);
            cozinhaBrasileira.setNome("Brasileira");

            Restaurante novoRestaurante = new Restaurante();
            novoRestaurante.setNome(null);
            novoRestaurante.setTaxaFrete(BigDecimal.valueOf(10.0));
            novoRestaurante.setCozinha(cozinhaBrasileira);

            novoRestaurante = restauranteService.salvar(novoRestaurante);
        });
    }


    private void preparingData() {
        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        burgerTopRestaurante = new Restaurante();
        burgerTopRestaurante.setNome("Burger Top");
        burgerTopRestaurante.setTaxaFrete(new BigDecimal(10));
        burgerTopRestaurante.setCozinha(cozinhaAmericana);
        restauranteRepository.save(burgerTopRestaurante);

        Restaurante comidaMineiraRestaurante = new Restaurante();
        comidaMineiraRestaurante.setNome("Comida Mineira");
        comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
        comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
        restauranteRepository.save(comidaMineiraRestaurante);
    }
}
