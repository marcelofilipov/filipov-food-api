package com.thefilipov.food.domain.repository;

import com.thefilipov.food.domain.model.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}
