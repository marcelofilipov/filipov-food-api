package com.thefilipov.food.domain.repository;

import java.util.List;

import com.thefilipov.food.domain.model.Estado;

public interface EstadoRepository {

	List<Estado> todos();
	Estado porId(Long id);
	Estado salvar(Estado estado);
	void remover(Long id);
	
}
