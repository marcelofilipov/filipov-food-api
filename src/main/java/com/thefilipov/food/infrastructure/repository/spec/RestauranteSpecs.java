package com.thefilipov.food.infrastructure.repository.spec;

import com.thefilipov.food.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import static com.thefilipov.food.utils.FoodUtils.*;

import java.math.BigDecimal;

public class RestauranteSpecs {

    public static Specification<Restaurante> comFreteGratis() {
        return (root, query, builder) ->
            builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }

    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return (root, query, builder) ->
            builder.like(root.get("nome"), contains(nome));
    }
}
