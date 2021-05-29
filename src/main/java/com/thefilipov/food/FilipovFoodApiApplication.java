package com.thefilipov.food;

import com.thefilipov.food.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class FilipovFoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilipovFoodApiApplication.class, args);
	}

}
