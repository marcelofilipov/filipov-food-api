package com.thefilipov.food.api.assembler;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestauranteIdInput {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;

}
