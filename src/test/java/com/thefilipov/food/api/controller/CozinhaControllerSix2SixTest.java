package com.thefilipov.food.api.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.thefilipov.food.api.assembler.CozinhaModelAssembler;
import com.thefilipov.food.api.model.CozinhaModel;
import com.thefilipov.food.domain.model.Cozinha;
import com.thefilipov.food.domain.repository.CozinhaRepository;
import com.thefilipov.food.domain.service.CadastroCozinhaService;
import com.thefilipov.food.templates.CozinhaTemplates;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class CozinhaControllerSix2SixTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private CozinhaController controller;

	@Mock
	private CadastroCozinhaService service;

	@Mock
	private CozinhaRepository repository;

	@Mock
	private CozinhaModelAssembler modelAssembler;

	@BeforeEach
	public void setUp() {
		FixtureFactoryLoader.loadTemplates(CozinhaTemplates.class.getPackage().getName());
	}

	@Test
	void fixtureAnyCozinha() {
		Cozinha cozinha = Fixture.from(Cozinha.class).gimme("anyCozinha");
		Assertions.assertNotNull(cozinha);
	}

	@Test
	public void fixtureCozinha() throws Exception {
		
		CozinhaModel cozinha = Fixture.from(CozinhaModel.class).gimme("anyCozinhaModel");
		assertThat(cozinha.getId()).isBetween(1L, 200L)
			.as("id must be between 1 and 200, value is: %s", cozinha.getId());
		assertThat(cozinha.getNome()).isNotBlank();
		
		//System.out.println(cozinha);
		
		List<CozinhaModel> quatroCozinhas = Fixture.from(CozinhaModel.class).gimme(4, "anyCozinhaModel");
		assertThat(quatroCozinhas).hasSize(4);
		
		//	quatroCozinhas.forEach(System.out::println);

	}

	@Test
	void endpoint200() throws Exception {

		List<CozinhaModel> quatroCozinhas = Fixture.from(CozinhaModel.class).gimme(4, "anyCozinhaModel");

		//when(controller.listar()).thenReturn(quatroCozinhas);

		this.mockMvc.perform(get(CozinhaController.URI)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.*", hasSize(4)));

	}

	@Test
	void givenCozinhas_whenGetCozinhas_thenStatus200() throws Exception {
		String uri = "/cozinhas";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(HttpStatus.OK.value(), status);
	}

	private List<Object> cozinhaClient() {
		FixtureFactoryLoader.loadTemplates(CozinhaTemplates.class.getPackage().getName());
		List<CozinhaModel> quatroCozinhas = Fixture.from(CozinhaModel.class).gimme(4, "anyCozinhaModel");

		return Arrays.asList(quatroCozinhas);
	}
	
}
