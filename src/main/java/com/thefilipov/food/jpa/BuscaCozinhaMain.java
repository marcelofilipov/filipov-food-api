package com.thefilipov.food.jpa;

import com.thefilipov.food.FilipovFoodApiApplication;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

public class BuscaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FilipovFoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

		Optional<Cozinha> cozinha = cozinhaRepository.findById(1L);

		System.out.println(cozinha.get().getNome());

	}

}
