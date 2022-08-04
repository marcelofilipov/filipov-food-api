package com.thefilipov.food.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.thefilipov.food.domain.model.*;

import java.math.BigDecimal;

import static java.time.OffsetDateTime.now;

public class RestauranteTemplates implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(Restaurante.class).addTemplate("oneRestaurante", new Rule() {{
            add("id", 1L);
            add("nome", "Restaurante Sabor do Brasil");
            add("taxaFrete",
                    random(BigDecimal.class, range(new BigDecimal("9.90"), new BigDecimal("19.99"))));
            add("dataCadastro", now());
            add("dataAtualizacao", now());
            add("ativo", random(Boolean.class, range(0, 1)));
            add("aberto", random(Boolean.class, range(0, 1)));
            add("cozinha", one(Cozinha.class, "oneCozinha"));
            add("endereco", one(Endereco.class, "oneEndereco"));
        }});

        Fixture.of(Cozinha.class).addTemplate("oneCozinha", new Rule() {{
            add("id", 1L);
            add("nome", "Brasileira");
        }});

        Fixture.of(Endereco.class).addTemplate("oneEndereco", new Rule() {{
            add("cep", random("09111340", "08330330"));
            add("logradouro", random("Rua Giovanni Battista Pirelli", "Rua Onze de Junho"));
            add("numero", random("1463", "501", "739"));
            add("complemento", random("Apto 54-D", "Fundos"));
            add("bairro", random("Casa Branca", "Vila Homero Thon"));
            add("cidade", one(Cidade.class, "oneCidade"));
        }});

        Fixture.of(Cidade.class).addTemplate("oneCidade", new Rule() {{
            add("id", 1L);
            add("nome", "São Paulo");
            add("estado", one(Estado.class, "oneEstado"));
        }});

        Fixture.of(Estado.class).addTemplate("oneEstado", new Rule() {{
            add("id", 1L);
            add("nome", "São Paulo");
        }});

    }

}
