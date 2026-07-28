package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.FoodLinks;
import com.thefilipov.food.api.controller.UsuarioController;
import com.thefilipov.food.api.model.UsuarioModel;
import com.thefilipov.food.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FoodLinks foodLinks;

	public UsuarioModelAssembler() {
		super(UsuarioController.class, UsuarioModel.class);
	}

	public UsuarioModel toModel(Usuario usuario) {
		var usuarioModel = createModelWithId(usuario.getId(), usuario);
		modelMapper.map(usuario, usuarioModel);

		usuarioModel.add(foodLinks.linkToUsuarios("usuarios"));

		usuarioModel.add(foodLinks.linkToGruposUsuario(usuario.getId(),"grupos-usuario"));

		return usuarioModel;
	}

	@Override
	public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
		if (entities == null || !entities.iterator().hasNext()) {
			return CollectionModel.empty();
		}

		return super.toCollectionModel(entities)
				.add(foodLinks.linkToUsuarios());
	}

}
