package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.FoodLinks;
import com.thefilipov.food.api.controller.RestauranteController;
import com.thefilipov.food.api.model.RestauranteModel;
import com.thefilipov.food.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(foodLinks.linkToRestaurantes("restaurantes"));

        restauranteModel.getCozinha().add(
                foodLinks.linkToCozinha(restaurante.getCozinha().getId()));

        restauranteModel.getEndereco().getCidade().add(
                foodLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));

        restauranteModel.add(foodLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
                "formas-pagamento"));

        restauranteModel.add(foodLinks.linkToResponsaveisRestaurante(restaurante.getId(),
                "responsaveis"));

        if (restaurante.ativacaoPermitida()) {
            restauranteModel.add(
                    foodLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        }

        if (restaurante.inativacaoPermitida()) {
            restauranteModel.add(
                    foodLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
        }

        if (restaurante.aberturaPermitida()) {
            restauranteModel.add(
                    foodLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }

        if (restaurante.fechamentoPermitido()) {
            restauranteModel.add(
                    foodLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(foodLinks.linkToRestaurantes());
    }
}