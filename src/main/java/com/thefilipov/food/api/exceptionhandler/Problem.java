package com.thefilipov.food.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "2023-04-07T17:30:38.173726928Z", position = 5)
    private OffsetDateTime timestamp;

    @ApiModelProperty(example = "https://filipovfood.com.br/entidade-em-uso", position = 10)
    private String type;

    @ApiModelProperty(example = "Entidade em uso", position = 15)
    private String title;

    @ApiModelProperty(example = "Estado de código 4 não pode ser removida, pois está em uso", position = 20)
    private String detail;

    @ApiModelProperty(example = "Estado de código 4 não pode ser removida, pois está em uso", position = 25)
    private String userMessage;

    @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position = 30)
    private List<Object> objects;

    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "preco")
        private String name;

        @ApiModelProperty(example = "O preço é obrigatório")
        private String userMessage;

    }

}
