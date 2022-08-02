package com.thefilipov.food.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.thefilipov.food.api.model.GrupoModel;
import com.thefilipov.food.domain.model.Grupo;

public class GrupoTemplates implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Grupo.class).addTemplate("oneGrupo", new Rule() {{
            add("id", 1L);
            add("nome", "Gerente");
        }});

        Fixture.of(GrupoModel.class).addTemplate("oneUsuarioModel", new Rule() {{
            add("id", 1L);
            add("nome", "Gerente");
        }});

        Fixture.of(Grupo.class).addTemplate("anyGrupo", new Rule() {{
            add("id", random(Long.class, range(1L, 100L)));
            add("nome", random("Gerente", "Vendedor", "Secretária", "Cadastrador"));
        }});

        Fixture.of(GrupoModel.class).addTemplate("anyGrupoModel", new Rule() {{
            add("id", random(Long.class, range(1L, 100L)));
            add("nome", random("Gerente", "Vendedor", "Secretária", "Cadastrador"));
        }});
    }

}
