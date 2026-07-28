package com.thefilipov.food.api.controller;

import com.thefilipov.food.api.assembler.CozinhaInputDisassembler;
import com.thefilipov.food.api.assembler.CozinhaModelAssembler;
import com.thefilipov.food.api.model.CozinhaModel;
import com.thefilipov.food.api.model.input.CozinhaInput;
import com.thefilipov.food.api.openapi.controller.CozinhaControllerDocumentation;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.repository.CozinhaRepository;
import com.thefilipov.food.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(CozinhaController.URI)
public class CozinhaController implements CozinhaControllerDocumentation {

	public static final String URI = "/cozinhas";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

		return pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelAssembler);
	}

	@GetMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		var cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		
		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		var cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		cozinha = cadastroCozinha.salvar(cozinha);
		
		return cozinhaModelAssembler.toModel(cozinha);
	}
	
	@PutMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
		var cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);
		
		return cozinhaModelAssembler.toModel(cozinhaAtual);
	}
	
	@DeleteMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}

}
