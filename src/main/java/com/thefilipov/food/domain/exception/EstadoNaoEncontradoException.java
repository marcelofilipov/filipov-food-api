package com.thefilipov.food.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_ESTADO_NAO_ENCONTRADA = "Não existe um Estado cadastrado com o código %d";

	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public EstadoNaoEncontradoException(Long estadoId) {
		this(String.format(MSG_ESTADO_NAO_ENCONTRADA, estadoId));
	}

}
