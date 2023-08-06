package com.thefilipov.food.api.controller;

import com.thefilipov.food.api.openapi.controller.StatusAplicacaoControllerDocumentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(StatusAplicacaoController.URI)
public class StatusAplicacaoController implements StatusAplicacaoControllerDocumentation {

	protected static final String URI = "v1/status";

	@GetMapping
	public String verificaStatusAplicacao() {
		return "Aplicação está funcionando corretamente";
	}

}
