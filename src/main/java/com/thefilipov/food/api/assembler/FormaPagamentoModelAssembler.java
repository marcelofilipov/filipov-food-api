package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.model.FormaPagamentoModel;
import com.thefilipov.food.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
	}
	
	public List<FormaPagamentoModel> toCollectionModel(Collection<FormaPagamento> formasPagamentos) {
		return formasPagamentos.stream()
			.map(formaPagamento -> toModel(formaPagamento))
			.collect(Collectors.toList());
	}
	
}
