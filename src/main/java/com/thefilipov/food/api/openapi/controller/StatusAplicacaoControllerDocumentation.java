package com.thefilipov.food.api.openapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Status Aplicação")
public interface StatusAplicacaoControllerDocumentation {

    @ApiOperation("Verifica status da aplicação")
    String verificaStatusAplicacao();

}
