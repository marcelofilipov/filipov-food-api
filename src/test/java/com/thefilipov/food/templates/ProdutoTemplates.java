package com.thefilipov.food.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.thefilipov.food.api.model.ProdutoModel;
import com.thefilipov.food.domain.model.Produto;

import java.math.BigDecimal;

public class ProdutoTemplates implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(Produto.class).addTemplate("oneProduto", new Rule() {{
            add("id", 1L);
            add("nome", "Porco com molho agridoce");
            add("descricao", "Deliciosa carne suína ao molho especial");
            add("preco",
                    random(BigDecimal.class, range(new BigDecimal("78.90"), new BigDecimal("110.67"))));
            add("ativo", random(Boolean.class, range(0, 1)));

        }});

        Fixture.of(ProdutoModel.class).addTemplate("oneProdutoModel", new Rule() {{
            add("id", 1L);
            add("nome", "Porco com molho agridoce");
            add("descricao", "Deliciosa carne suína ao molho especial");
            add("preco",
                    random(BigDecimal.class, range(new BigDecimal("78.90"), new BigDecimal("110.67"))));
            add("ativo", random(Boolean.class, range(0, 1)));
        }});

        Fixture.of(Produto.class).addTemplate("anyProduto", new Rule() {{
            add("id", random(Long.class, range(1L, 100L)));
            add("nome",
                    random("Porco com molho agridoce", "Camarão tailandês", "Salada picante com carne grelhada", "Garlic Naan"));
            add("descricao",
                    random("Deliciosa carne suína ao molho especial", "16 camarões grandes ao molho picante", "Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha", "Pão tradicional indiano com cobertura de alho"));
            add("preco",
                    random(BigDecimal.class, range(new BigDecimal("78.90"), new BigDecimal("110.67"))));
            add("ativo", random(Boolean.class, range(0, 1)));
        }});

        Fixture.of(ProdutoModel.class).addTemplate("anyProdutoModel", new Rule() {{
            add("id", random(Long.class, range(1L, 100L)));
            add("nome",
                    random("Porco com molho agridoce", "Camarão tailandês", "Salada picante com carne grelhada", "Garlic Naan"));
            add("descricao",
                    random("Deliciosa carne suína ao molho especial", "16 camarões grandes ao molho picante", "Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha", "Pão tradicional indiano com cobertura de alho"));
            add("preco",
                    random(BigDecimal.class, range(new BigDecimal("78.90"), new BigDecimal("110.67"))));
            add("ativo", random(Boolean.class, range(0, 1)));
        }});
    }

}
