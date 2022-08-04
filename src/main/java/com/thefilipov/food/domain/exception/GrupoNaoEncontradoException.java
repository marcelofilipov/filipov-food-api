package com.thefilipov.food.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_GRUPO_NAO_ENCONTRADO = "Não existe um Grupo cadastrado com o código %d";

	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public GrupoNaoEncontradoException(Long grupoId) {
		this(String.format(MSG_GRUPO_NAO_ENCONTRADO, grupoId));
	}

}
