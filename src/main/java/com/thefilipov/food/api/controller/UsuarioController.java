package com.thefilipov.food.api.controller;

import com.thefilipov.food.api.assembler.UsuarioInputDisassembler;
import com.thefilipov.food.api.assembler.UsuarioModelAssembler;
import com.thefilipov.food.api.model.UsuarioModel;
import com.thefilipov.food.api.model.input.SenhaInput;
import com.thefilipov.food.api.model.input.UsuarioComSenhaInput;
import com.thefilipov.food.api.model.input.UsuarioInput;
import com.thefilipov.food.domain.model.Usuario;
import com.thefilipov.food.domain.repository.UsuarioRepository;
import com.thefilipov.food.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;

	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;

	@GetMapping
	public ResponseEntity<List<UsuarioModel>> listar() {
		List<Usuario> todasUsuarios = usuarioRepository.findAll();

		List<UsuarioModel> usuariosModel = usuarioModelAssembler.toCollectionModel(todasUsuarios);

		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(15, TimeUnit.MINUTES).cachePrivate())
				.body(usuariosModel);
	}

    @GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
    	Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

    	return usuarioModelAssembler.toModel(usuario);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		usuario = cadastroUsuario.salvar(usuario);
		
		return usuarioModelAssembler.toModel(usuario);
	}

	@PutMapping("/{usuarioId}")
	public UsuarioModel atualizar(@PathVariable Long usuarioId,
			@RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
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
