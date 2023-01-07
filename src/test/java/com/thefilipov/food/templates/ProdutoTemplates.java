package com.thefilipov.food.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.thefilipov.food.api.model.ProdutoModel;
import com.thefilipov.food.domain.model.*;

import java.math.BigDecimal;

import static java.time.OffsetDateTime.now;

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
            add("restaurante", one(Restaurante.class, "oneRestaurante"));
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
