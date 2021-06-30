package com.thefilipov.food.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thefilipov.food.domain.model.Restaurante;

import java.util.List;

public abstract class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes;

}
