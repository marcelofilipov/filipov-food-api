package com.thefilipov.food.domain.service;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import com.thefilipov.food.domain.exception.EntidadeNaoEncontradaException;
import com.thefilipov.food.domain.model.FormaPagamento;
import com.thefilipov.food.domain.repository.FormaPagamentoRepository;
import com.thefilipov.food.util.DatabaseCleaner;
import com.thefilipov.food.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroFormaPagamentoIT {

    /**
     * RestAssured - API Test
     */

    private static final long FORMAPAGAMENTO_ID_INEXISTENTE = 100L;

    private FormaPagamento formaPagtoPIX;
    private int quantidadeFormasPagtoCadastradas;
    private String jsonCorretoFormaPagtoCD;

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private FormaPagamentoRepository formaPagtoRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/foodapi/formas-pagamento";

        databaseCleaner.clearTables();
        preparingData();

        jsonCorretoFormaPagtoCD = ResourceUtils.getContentFromResource("/json/ok/formapagto-cartaodebito.json");
    }

    @Test
    @DisplayName("Retornar Status 200 - Quando consultar formas de pagamento")
    public void shouldRetornarStatus200_whenConsultarFormasDePagto() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Retornar uma resposta e Status 200 - Quando consultar uma forma de pagamento existente")
    public void shouldRetornarUmaRespostaEStatus200_whenConsultarFormaDePagtoExistente() {
        given()
            .pathParam("formaPagtoId", formaPagtoPIX.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{formaPagtoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("descricao", equalTo(formaPagtoPIX.getDescricao()));
    }

    @Test
    @DisplayName("Retornar Status 404 - Quando consultar uma forma de pagamento inexistente")
    public void shouldRetornarStatus404_whenConsultarFormaDePagtoInexistente() {
        given()
            .pathParam("formaPagtoId", FORMAPAGAMENTO_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{formaPagtoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve retornar quantidade correta de Formas de Pagamento - Quando Consultar Formas de Pagto")
    public void shouldRetornarQuantidadeCorretaDeFormasDePagamento_whenConsultarFormasDePagto() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", hasSize(quantidadeFormasPagtoCadastradas))
            .body("descricao", hasItems("Dinheiro", "PIX"));
    }

    @Test
    @DisplayName("Retornar Status 201 - Quando cadastrar uma forma de pagamento")
    public void shouldRetornarStatus201_whenCadastrarFormaDePagto() {
        given()
            .body(jsonCorretoFormaPagtoCD)
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
    private CadastroFormaPagamentoService formaPagtoService;

    @Test
    @DisplayName("Quando Cadastrar Forma de Pagamento com dados corretos - Deve ser atribuído um Id")
    public void whenCadastroFormaDePagamentoComDadosCorretos_thenDeveAtribuirId() {
        // cenário
        FormaPagamento novaFormaPagto = new FormaPagamento();
        novaFormaPagto.setDescricao("Cheque Administrativo");

        // ação
        novaFormaPagto = formaPagtoService.salvar(novaFormaPagto);

        // validação
        assertThat(novaFormaPagto).isNotNull();
        assertThat(novaFormaPagto.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve Falhar - Quando tentar Cadastrar Forma de Pagamento sem descrição (NULL)")
    public void shouldFail_whenCadastrarFormaDePagtoSemDescricao() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            FormaPagamento novaFormaPagto = new FormaPagamento();
            novaFormaPagto.setDescricao(null);
            novaFormaPagto = formaPagtoService.salvar(novaFormaPagto);
        });
    }

    @Test
    @DisplayName("Falhar quando tentar Excluir uma Forma de Pagamento Inexistente")
    public void shouldFail_whenExcluirFormaDePagtoInexistente() {
        assertThrows(EntidadeNaoEncontradaException.class, () -> {
        	formaPagtoService.excluir(FORMAPAGAMENTO_ID_INEXISTENTE);
        });
    }

    private void preparingData() {
    	FormaPagamento formaPagtoDinheiro = new FormaPagamento();
    	formaPagtoDinheiro.setDescricao("Dinheiro");
    	formaPagtoRepository.save(formaPagtoDinheiro);

    	formaPagtoPIX = new FormaPagamento();
    	formaPagtoPIX.setDescricao("PIX");
        formaPagtoRepository.save(formaPagtoPIX);

    	FormaPagamento formaPagtoCheque = new FormaPagamento();
    	formaPagtoCheque.setDescricao("Cheque");
    	formaPagtoRepository.save(formaPagtoCheque);

    	FormaPagamento formaPagtoCC = new FormaPagamento();
    	formaPagtoCC.setDescricao("Cartão de Crédito");
    	formaPagtoRepository.save(formaPagtoCC);

        this.quantidadeFormasPagtoCadastradas = (int) formaPagtoRepository.count();
    }

}
