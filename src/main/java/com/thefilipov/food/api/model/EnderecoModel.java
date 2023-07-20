package com.thefilipov.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

    @ApiModelProperty(example = "09111-340")
    private String cep;

    @ApiModelProperty(example = "Rua Giovanni Battista Pirelli")
    private String logradouro;

    @ApiModelProperty(example = "1463")
    private String numero;

    @ApiModelProperty(example = "Apto 54-D")
    private String complemento;

    @ApiModelProperty(example = "Homero Thon")
    private String bairro;

    private CidadeResumoModel cidade;
    
}
