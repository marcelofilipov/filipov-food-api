package com.thefilipov.food.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;
    public static final String MSG_PEDIDO_NAO_ENCONTRADO = "Não existe um pedido com código %s";

    public PedidoNaoEncontradoException(String codigoPedido) {
        super(String.format(MSG_PEDIDO_NAO_ENCONTRADO, codigoPedido));
    }

}
