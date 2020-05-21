package com.thefilipov.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.thefilipov.food.domain.model.Cidade;
import com.thefilipov.food.domain.repository.CidadeRepository;

public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cidade> todas() {
		return manager.createQuery("from Cidade", Cidade.class)
				.getResultList();
	}

	@Override
	public Cidade porId(Long id) {
		return manager.find(Cidade.class, id);
	}

	@Transactional
	@Override
	public Cidade salvar(Cidade cidade) {
		return manager.merge(cidade);
	}

	@Transactional
	@Override
	public void remover(Cidade cidade) {
		cidade = porId(cidade.getId());
		manager.remove(cidade);
	}

}
