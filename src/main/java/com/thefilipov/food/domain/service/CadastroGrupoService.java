package com.thefilipov.food.domain.service;

import com.thefilipov.food.domain.exception.EntidadeEmUsoException;
import com.thefilipov.food.domain.exception.GrupoNaoEncontradoException;
import com.thefilipov.food.domain.model.Grupo;
import com.thefilipov.food.domain.model.Permissao;
import com.thefilipov.food.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroGrupoService {

	public static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removida, pois está em uso";

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private CadastroPermissaoService cadastroPermissao;

	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	@Transactional
	public void excluir(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
		
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_GRUPO_EM_USO, grupoId));
		}
	}

	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);

		grupo.removerPermissao(permissao);
	}

	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);

		grupo.adicionarPermissao(permissao);
	}

	public Grupo buscarOuFalhar(Long grupoId) {
		return grupoRepository.findById(grupoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}

}
