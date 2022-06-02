package com.thefilipov.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thefilipov.food.api.model.FormaPagamentoDTO;
import com.thefilipov.food.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
	
	public List<FormaPagamentoDTO> toCollectionModel(List<FormaPagamento> formasPagamentos) {
		return formasPagamentos.stream()
			.map(formaPagamento -> toModel(formaPagamento))
			.collect(Collectors.toList());
	}
	
}
