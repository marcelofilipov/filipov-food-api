package com.thefilipov.food.domain.repository;

import java.util.List;

import com.thefilipov.food.domain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> todas();
	List<Cozinha> consultarPorNome(String nome);
	Cozinha porId(Long id);
	Cozinha salvar(Cozinha cozinha);
	void remover(Long id);
	
}
