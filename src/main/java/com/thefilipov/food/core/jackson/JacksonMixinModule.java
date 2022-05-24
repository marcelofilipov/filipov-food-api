package com.thefilipov.food.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.thefilipov.food.api.model.mixin.CidadeMixin;
import com.thefilipov.food.domain.model.Cidade;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }
}
