package com.thefilipov.food.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.thefilipov.food.api.model.mixin.RestauranteMixin;
import com.thefilipov.food.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = -2221627815410527280L;

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }
}
