package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.model.input.RestauranteInput;
import com.thefilipov.food.domain.model.Cidade;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
    	return modelMapper.map(restauranteInput, Restaurante.class);
    }
    
    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
    	/*
    	 * Para evitar essa exception
    	 * org.springframework.orm.jpa.JpaSystemException: identifier of an instance of com.thefilipov.food.domain.model.Cozinha was altered from 3 to 1;
    	 */
    	restaurante.setCozinha(new Cozinha());
    	
    	if(restaurante.getEndereco() != null)
    		restaurante.getEndereco().setCidade(new Cidade());
    	
    	modelMapper.map(restauranteInput, restaurante);
    }

}
