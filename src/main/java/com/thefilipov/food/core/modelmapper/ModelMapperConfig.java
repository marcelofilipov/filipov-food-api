package com.thefilipov.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thefilipov.food.api.model.EnderecoDTO;
import com.thefilipov.food.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(
			Endereco.class, EnderecoDTO.class);
		enderecoToEnderecoDTOTypeMap.<String>addMapping(
			enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
			(enderecoDTODest, value) -> enderecoDTODest.getCidade().setEstado(value));
			
		return modelMapper;
	}

}
