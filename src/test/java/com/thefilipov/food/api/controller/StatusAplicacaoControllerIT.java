package com.thefilipov.food.api.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class StatusAplicacaoControllerIT {

    /**
     * RestAssured - API Test
     */

    @Value("${local.server.port}")
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/foodapi/v1/status";
    }

    @Test
    @DisplayName("Retornar a frase: Aplicação está funcionando corretamente")
    public void shouldRetornarFraseStatus_whenVerificarStatus() {
        Response response = given()
                .accept(ContentType.TEXT)
            .when()
                .get()
            .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Aplicação está funcionando corretamente",response.print());
    }

}
