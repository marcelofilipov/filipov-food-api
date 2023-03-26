package com.thefilipov.food.api.controller;

import com.thefilipov.food.domain.filter.VendaDiariaFilter;
import com.thefilipov.food.domain.service.VendaQueryService;
import com.thefilipov.food.domain.service.VendaReportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {EstatisticasController.class})
@ExtendWith(SpringExtension.class)
class EstatisticasControllerTest {
    @Autowired
    private EstatisticasController estatisticasController;

    @MockBean
    private VendaQueryService vendaQueryService;

    @MockBean
    private VendaReportService vendaReportService;

    /**
     * Method under test: {@link EstatisticasController#consultarVendasDiariasJson(VendaDiariaFilter, String)}
     */
    @Test
    @DisplayName("Retornar success(200) - Quando consultar Vendas Diárias")
    void getConsultarVendasDiarias() throws Exception {
        when(vendaQueryService.consultarVendasDiarias((VendaDiariaFilter) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/estatisticas/vendas-diarias");
        MockMvcBuilders.standaloneSetup(estatisticasController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link EstatisticasController#consultarVendasDiariasJson(VendaDiariaFilter, String)}
     */
    @Test
    @DisplayName("Retornar success(200) - Quando consultar Vendas Diárias com encoding")
    void getConsultarVendasDiariasWithEncoding() throws Exception {
        when(vendaQueryService.consultarVendasDiarias((VendaDiariaFilter) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/estatisticas/vendas-diarias");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(estatisticasController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

