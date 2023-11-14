package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.controller.UsuarioController;
import com.thefilipov.food.api.controller.UsuarioGrupoController;
import com.thefilipov.food.api.model.UsuarioModel;
import com.thefilipov.food.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

	@Autowired
	private ModelMapper modelMapper;

	public UsuarioModelAssembler() {
		super(UsuarioController.class, UsuarioModel.class);
	}

	public UsuarioModel toModel(Usuario usuario) {
		UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);

		modelMapper.map(usuario, usuarioModel);

		usuarioModel.add(linkTo(UsuarioController.class).withRel("usuarios"));

		usuarioModel.add(linkTo(methodOn(UsuarioGrupoController.class)
				.listar(usuario.getId())).withRel("grupos-usuario"));

		return usuarioModel;
	}

	public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(UsuarioController.class).withSelfRel());
	}        

}
