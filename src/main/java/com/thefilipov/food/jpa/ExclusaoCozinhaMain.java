package com.thefilipov.food.jpa;

import com.thefilipov.food.FilipovFoodApiApplication;
import com.thefilipov.food.domain.service.CadastroCozinhaService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ExclusaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FilipovFoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		CadastroCozinhaService cozinhas = applicationContext.getBean(CadastroCozinhaService.class);

		cozinhas.excluir(3L);
	}

}
