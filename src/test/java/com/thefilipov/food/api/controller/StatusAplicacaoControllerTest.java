package com.thefilipov.food.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {StatusAplicacaoController.class})
@ExtendWith(SpringExtension.class)
class StatusAplicacaoControllerTest {
    @Autowired
    private StatusAplicacaoController statusAplicacaoController;

    /**
     * Method under test: {@link StatusAplicacaoController#verificaStatusAplicacao()}
     */
    @Test
    void testVerificaStatusAplicacao() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/status");
        MockMvcBuilders.standaloneSetup(statusAplicacaoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Aplicação está funcionando corretamente"));
    }

    /**
     * Method under test: {@link StatusAplicacaoController#verificaStatusAplicacao()}
     */
    @Test
    void testVerificaStatusAplicacaoWithUriVariables() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/status", "Uri Variables");
        MockMvcBuilders.standaloneSetup(statusAplicacaoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Aplicação está funcionando corretamente"));
    }
}

