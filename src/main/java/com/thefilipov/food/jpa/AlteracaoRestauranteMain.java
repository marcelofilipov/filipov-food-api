package com.thefilipov.food.jpa;

import com.thefilipov.food.FilipovFoodApiApplication;
import com.thefilipov.food.domain.model.Restaurante;
import com.thefilipov.food.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

public class AlteracaoRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FilipovFoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

		Restaurante restaurante = new Restaurante();
		restaurante.setId(10L);
		restaurante.setNome("Restaurante Árabe");
		restaurante.setTaxaFrete(BigDecimal.valueOf(7.97));
		
		restaurante = restauranteRepository.save(restaurante);
		
		System.out.printf("%d - %s - %f\n", restaurante.getId(), restaurante.getNome(), restaurante.getTaxaFrete());
	}

}
