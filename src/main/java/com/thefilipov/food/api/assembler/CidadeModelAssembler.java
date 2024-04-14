package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.controller.CidadeController;
import com.thefilipov.food.api.controller.EstadoController;
import com.thefilipov.food.api.model.CidadeModel;
import com.thefilipov.food.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	@Autowired
    private ModelMapper modelMapper;

    public CidadeModelAssembler() {
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        var cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(linkTo(methodOn(CidadeController.class).listar()).withRel("cidades"));

        cidadeModel.add(linkTo(methodOn(EstadoController.class)
                .buscar(cidadeModel.getEstado().getId())).withSelfRel());

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CidadeController.class).withSelfRel());
    }

}
