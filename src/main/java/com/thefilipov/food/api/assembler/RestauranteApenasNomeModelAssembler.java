package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.FoodLinks;
import com.thefilipov.food.api.controller.RestauranteController;
import com.thefilipov.food.api.model.RestauranteApenasNomeModel;
import com.thefilipov.food.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteApenasNomeModelAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public RestauranteApenasNomeModelAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeModel.class);
    }

    @Override
    public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
        RestauranteApenasNomeModel restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(foodLinks.linkToRestaurantes("restaurantes"));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(foodLinks.linkToRestaurantes());
    }
}
