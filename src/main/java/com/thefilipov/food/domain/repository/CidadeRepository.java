package com.thefilipov.food.domain.repository;

import java.util.List;

import com.thefilipov.food.domain.model.Cidade;

public interface CidadeRepository {

	List<Cidade> todas();
	Cidade porId(Long id);
	Cidade salvar(Cidade cidade);
	void remover(Long id);
	
}
