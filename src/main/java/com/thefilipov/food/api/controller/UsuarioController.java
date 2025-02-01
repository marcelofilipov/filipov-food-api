package com.thefilipov.food.api.controller;

import com.thefilipov.food.api.assembler.UsuarioInputDisassembler;
import com.thefilipov.food.api.assembler.UsuarioModelAssembler;
import com.thefilipov.food.api.model.UsuarioModel;
import com.thefilipov.food.api.model.input.SenhaInput;
import com.thefilipov.food.api.model.input.UsuarioComSenhaInput;
import com.thefilipov.food.api.model.input.UsuarioInput;
import com.thefilipov.food.api.openapi.controller.UsuarioControllerDocumentation;
import com.thefilipov.food.domain.model.Usuario;
import com.thefilipov.food.domain.repository.UsuarioRepository;
import com.thefilipov.food.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path =UsuarioController.URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerDocumentation {

	public static final String URI = "/usuarios";

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;

	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;

	@GetMapping
	public CollectionModel<UsuarioModel> listar() {
		List<Usuario> todasUsuarios = usuarioRepository.findAll();

		return usuarioModelAssembler.toCollectionModel(todasUsuarios);
	}

    @GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
    	var usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

    	return usuarioModelAssembler.toModel(usuario);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
		var usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		usuario = cadastroUsuario.salvar(usuario);
		
		return usuarioModelAssembler.toModel(usuario);
	}

	@PutMapping("/{usuarioId}")
	public UsuarioModel atualizar(@PathVariable Long usuarioId,
			@RequestBody @Valid UsuarioInput usuarioInput) {
		var usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
		usuarioAtual = cadastroUsuario.salvar(usuarioAtual);
		
		return usuarioModelAssembler.toModel(usuarioAtual);
	}

	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
		cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
	}

}              
