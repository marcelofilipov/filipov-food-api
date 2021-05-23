package com.thefilipov.food.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thefilipov.food.domain.exception.EntidadeNaoEncontradaException;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.model.Restaurante;
import com.thefilipov.food.domain.repository.CozinhaRepository;
import com.thefilipov.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));

		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.salvar(restaurante);
	}
	
	public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {
		Restaurante restauranteAtual = restauranteRepository.porId(restauranteId);

		if (restauranteAtual == null) {
			throw new EntidadeNaoEncontradaException(
				String.format("Não existe cadastro de restaurante com código %d", restauranteId));
		}
		
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
		restauranteAtual = salvar(restauranteAtual);

		return restauranteAtual;
	}

}
