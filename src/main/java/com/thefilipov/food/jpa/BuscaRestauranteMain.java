package com.thefilipov.food.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.thefilipov.food.FilipovFoodApiApplication;
import com.thefilipov.food.domain.model.Restaurante;
import com.thefilipov.food.domain.repository.RestauranteRepository;

public class BuscaRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FilipovFoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

		Restaurante restaurante = restauranteRepository.porId(1L);

		System.out.println(restaurante.getNome() + " - " + restaurante.getTaxaFrete());

	}

}
