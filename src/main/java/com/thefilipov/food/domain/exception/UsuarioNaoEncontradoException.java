package com.thefilipov.food.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_USUARIO_NAO_ENCONTRADO = "Não existe um cadastro de Usuário com o código %d";

	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public UsuarioNaoEncontradoException(Long estadoId) {
		this(String.format(MSG_USUARIO_NAO_ENCONTRADO, estadoId));
	}

}
