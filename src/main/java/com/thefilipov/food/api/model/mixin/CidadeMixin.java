package com.thefilipov.food.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thefilipov.food.domain.model.Estado;

public abstract class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;

}
