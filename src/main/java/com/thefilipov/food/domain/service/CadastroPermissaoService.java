package com.thefilipov.food.domain.service;

import com.thefilipov.food.domain.exception.PermissaoNaoEncontradaException;
import com.thefilipov.food.domain.model.Permissao;
import com.thefilipov.food.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }

}
