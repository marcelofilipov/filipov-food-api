package com.thefilipov.food.api.controller;

import com.thefilipov.food.api.FoodLinks;
import com.thefilipov.food.api.assembler.UsuarioModelAssembler;
import com.thefilipov.food.api.model.UsuarioModel;
import com.thefilipov.food.api.openapi.controller.RestauranteUsuarioResponsavelControllerDocumentation;
import com.thefilipov.food.domain.model.Usuario;
import com.thefilipov.food.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = RestauranteUsuarioResponsavelController.URI)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerDocumentation {

    public static final String URI = "/restaurantes/{restauranteId}/responsaveis";

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private FoodLinks foodLinks;

    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        var responsaveis = restaurante.getResponsaveis() != null ? restaurante.getResponsaveis() : List.of();

        return usuarioModelAssembler.toCollectionModel((Iterable<? extends Usuario>) responsaveis)
                .removeLinks()
                .add(foodLinks.linkToResponsaveisRestaurante(restauranteId));
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
    }

}
