package com.thefilipov.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.exception.EntidadeNaoEncontradaException;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.repository.CozinhaRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCozinhaService {

	public static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe um cadastro de Cozinha com o código %d";
	public static final String MSG_COZINHA_EM_USO = "Cozinha de código  %d não pode ser removida, pois está em uso";

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	@Transactional
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
		
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}

	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId)));
	}
}
