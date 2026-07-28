package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.FoodLinks;
import com.thefilipov.food.api.controller.CozinhaController;
import com.thefilipov.food.api.model.CozinhaModel;
import com.thefilipov.food.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssembler
        extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

	@Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FoodLinks foodLinks;

    public CozinhaModelAssembler() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        var cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(foodLinks.linkToCozinhas("cozinhas"));

        return cozinhaModel;
    }

}
