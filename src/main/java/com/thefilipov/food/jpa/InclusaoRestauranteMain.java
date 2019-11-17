package com.thefilipov.food.jpa;

import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.thefilipov.food.FilipovFoodApiApplication;
import com.thefilipov.food.domain.model.Restaurante;
import com.thefilipov.food.domain.model.repository.RestauranteRepository;

public class InclusaoRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FilipovFoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Sabores da Rússia");
		restaurante1.setTaxaFrete(BigDecimal.valueOf(19.99));
		
		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Sabores do Japão");
		restaurante2.setTaxaFrete(BigDecimal.valueOf(9.98));
		
		restaurante1 = restauranteRepository.salvar(restaurante1);
		restaurante2 = restauranteRepository.salvar(restaurante2);
		
		System.out.printf("%d - %s - %f\n", restaurante1.getId(), restaurante1.getNome(), restaurante1.getTaxaFrete());
		System.out.printf("%d - %s - %f\n", restaurante2.getId(), restaurante2.getNome(), restaurante2.getTaxaFrete());
		
	}

}
