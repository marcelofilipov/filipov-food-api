package com.thefilipov.food.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class EstadoInput {

	@ApiModelProperty(example = "São Paulo", required = true)
	@NotBlank
	private String nome;

}
