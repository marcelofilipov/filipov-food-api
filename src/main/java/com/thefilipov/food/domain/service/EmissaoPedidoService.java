package com.thefilipov.food.domain.service;

import com.thefilipov.food.domain.exception.PedidoNaoEncontradoException;
import com.thefilipov.food.domain.model.Pedido;
import com.thefilipov.food.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

}
