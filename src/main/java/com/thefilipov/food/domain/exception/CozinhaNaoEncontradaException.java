package com.thefilipov.food.domain.exception;
public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_ESTADO_NAO_ENCONTRADA = "Não existe um cadastro de Estado com o código %d";

	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public CozinhaNaoEncontradaException(Long estadoId) {
		this(String.format(MSG_ESTADO_NAO_ENCONTRADA, estadoId));
	}

}
