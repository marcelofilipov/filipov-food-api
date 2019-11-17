package com.thefilipov.food.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.thefilipov.food.FilipovFoodApiApplication;
import com.thefilipov.food.domain.model.Restaurante;
import com.thefilipov.food.domain.model.repository.RestauranteRepository;

public class ConsultaRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FilipovFoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		RestauranteRepository restaurantes = applicationContext.getBean(RestauranteRepository.class);

		List<Restaurante> todasRestaurantes = restaurantes.todas();

		for (Restaurante restaurante : todasRestaurantes) {
			System.out.println(restaurante.getNome() + " - " + restaurante.getTaxaFrete());
		}

	}

}
