package com.thefilipov.food.domain.service;

import com.thefilipov.food.domain.filter.VendaDiariaFilter;
import com.thefilipov.food.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);

}
