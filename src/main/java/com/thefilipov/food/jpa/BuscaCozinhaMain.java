package com.thefilipov.food.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.thefilipov.food.FilipovFoodApiApplication;
import com.thefilipov.food.domain.model.Cozinha;

public class BuscaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FilipovFoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);

		Cozinha cozinha = cadastroCozinha.buscar(1L);

		System.out.println(cozinha.getNome());

	}

}
