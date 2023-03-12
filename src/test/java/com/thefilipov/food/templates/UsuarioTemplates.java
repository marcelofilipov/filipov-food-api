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
            add("senha", "(S2Bq53>");
        }});

        Fixture.of(UsuarioModel.class).addTemplate("oneUsuarioModel", new Rule() {{
            add("id", 1L);
            add("nome", "Maria Joaquina");
            add("email", "maria.vnd@filfood.com");
        }});

        Fixture.of(Usuario.class).addTemplate("anyUsuario", new Rule() {{
            add("id", random(Long.class, range(1L, 100L)));
            add("nome", random("Emídio Ribeiro", "Edgar Denis", "Humberto Cacioli", "Aldemar da Cunha"));
            add("email", random("email1@mail.com", "email2@mail.com", "email3@mail.com", "email4@mail.com"));
            add("senha", random("B8W{R#8W","W+Hi7(R}",";^IJ.+1!","(fD3={E!"));
        }});

        Fixture.of(UsuarioModel.class).addTemplate("anyUsuarioModel", new Rule() {{
            add("id", random(Long.class, range(1L, 100L)));
            add("nome", random("Emídio Ribeiro", "Edgar Denis", "Humberto Cacioli", "Aldemar da Cunha"));
            add("email", random("email1@mail.com", "email2@mail.com", "email3@mail.com", "email4@mail.com"));
        }});

    }

}
