package com.thefilipov.food.jpa;

import com.thefilipov.food.FilipovFoodApiApplication;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FilipovFoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);

		List<Cozinha> todasCozinhas = cozinhas.findAll();

		todasCozinhas.stream()
				.forEach(System.out::println);

		todasCozinhas.stream()
				.forEach(c -> System.out.println(c.getNome()));
	}

}
