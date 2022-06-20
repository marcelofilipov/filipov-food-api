package com.thefilipov.food.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe uma Cozinha cadastrada com o código %d";

	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public CozinhaNaoEncontradaException(Long estadoId) {
		this(String.format(MSG_COZINHA_NAO_ENCONTRADA, estadoId));
	}

}
