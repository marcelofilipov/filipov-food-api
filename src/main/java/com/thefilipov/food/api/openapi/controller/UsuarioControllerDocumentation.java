package com.thefilipov.food.api.openapi.controller;

import com.thefilipov.food.api.exceptionhandler.Problem;
import com.thefilipov.food.api.model.UsuarioModel;
import com.thefilipov.food.api.model.input.SenhaInput;
import com.thefilipov.food.api.model.input.UsuarioComSenhaInput;
import com.thefilipov.food.api.model.input.UsuarioInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Usuários")
public interface UsuarioControllerDocumentation {

    @ApiOperation("Lista os usuários")
    CollectionModel<UsuarioModel> listar();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do usuário inválido",
                    content = @Content(schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    UsuarioModel buscar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId);

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
    })
    UsuarioModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true)
            UsuarioComSenhaInput usuarioInput);

    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    UsuarioModel atualizar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,

            @ApiParam(name = "corpo", value = "Representação de um novo usuário com os novos dados", required = true)
            UsuarioInput usuarioInput);

    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha alerada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    void alterarSenha(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,

            @ApiParam(name = "corpo", value = "Representação de uma nova senha", required = true)
            SenhaInput senha);

}
