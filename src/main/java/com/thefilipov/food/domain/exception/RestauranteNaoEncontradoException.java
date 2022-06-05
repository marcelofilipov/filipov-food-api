package com.thefilipov.food.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_RESTAURANTE_NAO_ENCONTRADA = "Não existe um cadastro de Restaurante com o código %d";

	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public RestauranteNaoEncontradoException(Long estadoId) {
		this(String.format(MSG_RESTAURANTE_NAO_ENCONTRADA, estadoId));
	}

}
