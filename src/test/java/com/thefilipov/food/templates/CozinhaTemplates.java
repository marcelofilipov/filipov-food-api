package com.thefilipov.food.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.thefilipov.food.api.model.CozinhaModel;
import com.thefilipov.food.domain.model.Cozinha;

public class CozinhaTemplates implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Cozinha.class).addTemplate("oneCozinha", new Rule() {{
			add("id", 1L);
			add("nome", "Brasileira");
		}});

		Fixture.of(CozinhaModel.class).addTemplate("oneCozinhaModel", new Rule() {{
			add("id", 1L);
			add("nome", "Brasileira");
		}});

		Fixture.of(Cozinha.class).addTemplate("anyCozinha", new Rule() {{
			add("id", random(Long.class, range(1L, 100L)));
			add("nome", random("Brasileira", "Mexicana", "Mineira", "Árabe"));
		}});

		Fixture.of(CozinhaModel.class).addTemplate("anyCozinhaModel", new Rule() {{
			add("id", random(Long.class, range(1L, 100L)));
			add("nome", random("Brasileira", "Mexicana", "Mineira", "Árabe"));
		}});
	}

}
