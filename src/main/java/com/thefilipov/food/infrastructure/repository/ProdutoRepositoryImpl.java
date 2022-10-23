package com.thefilipov.food.infrastructure.repository;

import com.thefilipov.food.domain.model.FotoProduto;
import com.thefilipov.food.domain.repository.ProdutoRepositoryCustomized;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryCustomized {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto foto) {
        return manager.merge(foto);
    }

}
