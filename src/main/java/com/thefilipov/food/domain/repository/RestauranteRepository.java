package com.thefilipov.food.domain.repository;

import java.util.List;

import com.thefilipov.food.domain.model.Restaurante;

public interface RestauranteRepository {

	List<Restaurante> todas();
	Restaurante porId(Long id);
	Restaurante salvar(Restaurante restaurante);
	void remover(Restaurante restaurante);
	
}
