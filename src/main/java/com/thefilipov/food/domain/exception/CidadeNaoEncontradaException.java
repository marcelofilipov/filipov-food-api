package com.thefilipov.food.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de Cidade com o código %d";

	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public CidadeNaoEncontradaException(Long estadoId) {
		this(String.format(MSG_CIDADE_NAO_ENCONTRADA, estadoId));
	}

}
