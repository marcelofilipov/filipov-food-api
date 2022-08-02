package com.thefilipov.food.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.thefilipov.food.api.model.UsuarioModel;
import com.thefilipov.food.domain.model.Usuario;

public class UsuarioTemplates implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(Usuario.class).addTemplate("oneUsuario", new Rule() {{
            add("id", 1L);
            add("nome", "Maria Joaquina");
            add("email", "maria.vnd@filfood.com");
            add("senha", "123");
        }});

        Fixture.of(UsuarioModel.class).addTemplate("oneUsuarioModel", new Rule() {{
            add("id", 1L);
            add("nome", "Maria Joaquina");
            add("email", "maria.vnd@filfood.com");
            add("senha", "123");
        }});

    }

}
