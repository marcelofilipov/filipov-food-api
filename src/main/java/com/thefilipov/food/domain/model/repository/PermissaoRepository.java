package com.thefilipov.food.domain.model.repository;

import java.util.List;

import com.thefilipov.food.domain.model.Permissao;

public interface PermissaoRepository {

	List<Permissao> todas();
	Permissao porId(Long id);
	Permissao salvar(Permissao permissao);
	void remover(Permissao permissao);
	
}
