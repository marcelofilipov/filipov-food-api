package com.thefilipov.food.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoInput {

    @ApiModelProperty(example = "09111-340", required = true)
	@NotBlank
    private String cep;

    @ApiModelProperty(example = "Rua Giovanni Battista Pirelli", required = true)
	@NotBlank
    private String logradouro;

    @ApiModelProperty(example = "1463", required = true)
	@NotBlank
    private String numero;

    @ApiModelProperty(example = "Apto 54-D")
    private String complemento;

    @ApiModelProperty(example = "Homero Thon", required = true)
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;
	
}
