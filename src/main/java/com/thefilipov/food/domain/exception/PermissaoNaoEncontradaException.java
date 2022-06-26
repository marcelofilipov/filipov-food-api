package com.thefilipov.food.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;
    public static final String MSG_PERMISSAO_NAO_ENCONTRADA = "Não existe um cadastro de permissão com código %d";

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long permissaoId) {
        this(String.format(MSG_PERMISSAO_NAO_ENCONTRADA, permissaoId));
    }
}
