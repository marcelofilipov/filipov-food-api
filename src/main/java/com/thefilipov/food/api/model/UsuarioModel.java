package com.thefilipov.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios")
@Getter
@Setter
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "João de Oliveira")
	private String nome;

	@ApiModelProperty(example = "joao.oliveira@gmail.com")
	private String email;

}
