package com.thefilipov.food.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.thefilipov.food.api.model.mixin.CidadeMixin;
import com.thefilipov.food.api.model.mixin.CozinhaMixin;
import com.thefilipov.food.domain.model.Cidade;
import com.thefilipov.food.domain.model.Cozinha;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = -2221627815410527280L;

    public JacksonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
