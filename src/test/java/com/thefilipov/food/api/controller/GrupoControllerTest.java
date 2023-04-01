package com.thefilipov.food.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefilipov.food.api.assembler.GrupoInputDisassembler;
import com.thefilipov.food.api.assembler.GrupoModelAssembler;
import com.thefilipov.food.api.model.GrupoModel;
import com.thefilipov.food.api.model.input.GrupoInput;
import com.thefilipov.food.domain.model.Grupo;
import com.thefilipov.food.domain.repository.GrupoRepository;
import com.thefilipov.food.domain.service.CadastroGrupoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {GrupoController.class})
@ExtendWith(SpringExtension.class)
class GrupoControllerTest {
    @MockBean
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private GrupoController grupoController;

    @MockBean
    private GrupoInputDisassembler grupoInputDisassembler;

    @MockBean
    private GrupoModelAssembler grupoModelAssembler;

    @MockBean
    private GrupoRepository grupoRepository;

    /**
     * Method under test: {@link GrupoController#adicionar(GrupoInput)}
     */
    @Test
    @DisplayName("Retornar created(201) - Quando criar grupo")
    void createGrupoAPI_RetornarStatus201() throws Exception {
        when(grupoModelAssembler.toCollectionModel(Mockito.<Collection<Grupo>>any())).thenReturn(new ArrayList<>());
        when(grupoRepository.findAll()).thenReturn(new ArrayList<>());

        GrupoInput grupoInput = new GrupoInput();
        grupoInput.setNome(UsuarioControllerTest.NOME);
        String content = (new ObjectMapper()).writeValueAsString(grupoInput);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/grupos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(grupoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }

    /**
     * Method under test: {@link GrupoController#atualizar(Long, GrupoInput)}
     */
    @Test
    @DisplayName("Retornar success(200) - Quando alterar grupo")
    void putGrupoAPI_RetornarStatus200() throws Exception {
        Grupo grupo = new Grupo();
        grupo.setId(1L);
        grupo.setNome(UsuarioControllerTest.NOME);
        grupo.setPermissoes(new HashSet<>());

        Grupo grupo2 = new Grupo();
        grupo2.setId(1L);
        grupo2.setNome(UsuarioControllerTest.NOME);
        grupo2.setPermissoes(new HashSet<>());
        when(cadastroGrupoService.buscarOuFalhar(Mockito.<Long>any())).thenReturn(grupo);
        when(cadastroGrupoService.salvar(Mockito.<Grupo>any())).thenReturn(grupo2);
        doNothing().when(grupoInputDisassembler).copyToDomainObject(Mockito.<GrupoInput>any(), Mockito.<Grupo>any());

        GrupoModel grupoModel = new GrupoModel();
        grupoModel.setId(1L);
        grupoModel.setNome(UsuarioControllerTest.NOME);
        when(grupoModelAssembler.toModel(Mockito.<Grupo>any())).thenReturn(grupoModel);

        GrupoInput grupoInput = new GrupoInput();
        grupoInput.setNome(UsuarioControllerTest.NOME);
        String content = (new ObjectMapper()).writeValueAsString(grupoInput);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/grupos/{grupoId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(grupoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<GrupoModel><id>1</id><nome>Nome</nome></GrupoModel>"));
    }

    /**
     * Method under test: {@link GrupoController#buscar(Long)}
     */
    @Test
    @DisplayName("Retornar success(200) - Quando buscar 1 grupo")
    void getGrupoByIdAPI_RetornarStatus200() throws Exception {
        Grupo grupo = new Grupo();
        grupo.setId(1L);
        grupo.setNome(UsuarioControllerTest.NOME);
        grupo.setPermissoes(new HashSet<>());
        when(cadastroGrupoService.buscarOuFalhar(Mockito.<Long>any())).thenReturn(grupo);

        GrupoModel grupoModel = new GrupoModel();
        grupoModel.setId(1L);
        grupoModel.setNome(UsuarioControllerTest.NOME);
        when(grupoModelAssembler.toModel(Mockito.<Grupo>any())).thenReturn(grupoModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/grupos/{grupoId}", 1L);
        MockMvcBuilders.standaloneSetup(grupoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<GrupoModel><id>1</id><nome>Nome</nome></GrupoModel>"));
    }

    /**
     * Method under test: {@link GrupoController#listar()}
     */
    @Test
    @DisplayName("Retornar success(200) - Quando buscar todos os grupos")
    void getAllGruposAPI_RetornarStatus200() throws Exception {
        when(grupoModelAssembler.toCollectionModel(Mockito.<Collection<Grupo>>any())).thenReturn(new ArrayList<>());
        when(grupoRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/grupos");
        MockMvcBuilders.standaloneSetup(grupoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }

    /**
     * Method under test: {@link GrupoController#listar()}
     */
    @Test
    @DisplayName("Retornar success(200) - Quando buscar todos os grupos com Encoding")
    void getAllGruposAPI_RetornarStatus200WithEncoding() throws Exception {
        when(grupoModelAssembler.toCollectionModel(Mockito.<Collection<Grupo>>any())).thenReturn(new ArrayList<>());
        when(grupoRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/grupos");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(grupoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }

    /**
     * Method under test: {@link GrupoController#remover(Long)}
     */
    @Test
    @DisplayName("Retornar nocontent(204) - Quando excluir grupo")
    void deleteGrupoAPI_RetornarStatus204() throws Exception {
        doNothing().when(cadastroGrupoService).excluir(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/grupos/{grupoId}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(grupoController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link GrupoController#remover(Long)}
     */
    @Test
    @DisplayName("Retornar nocontent(204) - Quando excluir grupo com Encoding")
    void deleteGrupoAPI_RetornarStatus204WithEncoding() throws Exception {
        doNothing().when(cadastroGrupoService).excluir(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/grupos/{grupoId}", 1L);
        requestBuilder.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(grupoController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

