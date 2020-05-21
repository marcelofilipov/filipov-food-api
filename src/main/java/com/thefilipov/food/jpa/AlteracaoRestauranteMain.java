package com.thefilipov.food.jpa;

import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.thefilipov.food.FilipovFoodApiApplication;
import com.thefilipov.food.domain.model.Restaurante;
import com.thefilipov.food.domain.repository.RestauranteRepository;

public class AlteracaoRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FilipovFoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

		Restaurante restaurante = new Restaurante();
		restaurante.setId(1L);
		restaurante.setNome("Restaurante Árabe");
		restaurante.setTaxaFrete(BigDecimal.valueOf(7.97));
		
		restaurante = restauranteRepository.salvar(restaurante);
		
		System.out.printf("%d - %s - %f\n", restaurante.getId(), restaurante.getNome(), restaurante.getTaxaFrete());
	}

}
