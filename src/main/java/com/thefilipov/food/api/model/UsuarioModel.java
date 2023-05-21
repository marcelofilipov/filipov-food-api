package com.thefilipov.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "João de Oliveira")
	private String nome;

	@ApiModelProperty(example = "joao.oliveira@gmail.com")
	private String email;

}
