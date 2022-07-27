package com.thefilipov.food.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;
    public static final String MSG_PEDIDO_NAO_ENCONTRADO = "Não existe um pedido com código %d";

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradoException(Long pedidoId) {
        this(String.format(MSG_PEDIDO_NAO_ENCONTRADO, pedidoId));
    }

}
