package com.thefilipov.food.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.thefilipov.food.FilipovFoodApiApplication;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.model.repository.CozinhaRepository;

public class AlteracaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FilipovFoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);
		cozinha.setNome("Brasileira");
		
		cozinha = cozinhaRepository.salvar(cozinha);
		
		System.out.printf("%d - %s\n", cozinha.getId(), cozinha.getNome());
	}

}
