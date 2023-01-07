package com.thefilipov.food.domain.repository;

import com.thefilipov.food.domain.model.FotoProduto;

public interface ProdutoRepositoryCustomized {

    FotoProduto save(FotoProduto foto);
    void delete(FotoProduto foto);

}
