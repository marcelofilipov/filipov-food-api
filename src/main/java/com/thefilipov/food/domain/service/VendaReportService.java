package com.thefilipov.food.domain.service;

import com.thefilipov.food.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);

}
