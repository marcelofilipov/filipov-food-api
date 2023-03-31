package com.thefilipov.food.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.thefilipov.food"))
                    .paths(PathSelectors.any())
                    //.paths(PathSelectors.ant("/cozinhas/*"))
                    .build()
                .apiInfo(apiInfo())
                .tags(new Tag("Cidades", "Gerencia as cidades"));
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Filipov Food API")
                .description("API pública para clientes e restaurantes")
                .version("1")
                .contact(new Contact("Marcelo Filipov", null, "marcelo@filipov.dev.br"))
                .build();

    }

}
