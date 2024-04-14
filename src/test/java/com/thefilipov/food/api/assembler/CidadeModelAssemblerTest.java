package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.model.CidadeModel;
import com.thefilipov.food.api.model.EstadoModel;
import com.thefilipov.food.domain.model.Cidade;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Disabled
class CidadeModelAssemblerTest {

    public static final long ID = 0L;
    public static final String NOME = "Santo André";
    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private CidadeModelAssembler cidadeModelAssemblerUnderTest;

    @Test
    void testToModel() {
        // Setup
        final Cidade cidade = new Cidade(ID, NOME);

        // Configure ModelMapper.map(...).
        final CidadeModel cidadeModel = new CidadeModel();
        cidadeModel.setId(ID);
        cidadeModel.setNome(NOME);
        final EstadoModel estado = new EstadoModel();
        estado.setId(ID);
        estado.setNome(NOME);
        cidadeModel.setEstado(estado);
        when(mockModelMapper.map(new Cidade(ID, NOME), CidadeModel.class)).thenReturn(cidadeModel);

        // Run the test
        final CidadeModel result = cidadeModelAssemblerUnderTest.toModel(cidade);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testToModel_ModelMapperThrowsConfigurationException() {
        // Setup
        final Cidade cidade = new Cidade(ID, NOME);
        when(mockModelMapper.map(new Cidade(ID, NOME), CidadeModel.class)).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> cidadeModelAssemblerUnderTest.toModel(cidade))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToModel_ModelMapperThrowsMappingException() {
        // Setup
        final Cidade cidade = new Cidade(ID, NOME);
        when(mockModelMapper.map(new Cidade(ID, NOME), CidadeModel.class)).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> cidadeModelAssemblerUnderTest.toModel(cidade)).isInstanceOf(MappingException.class);
    }

    @Test
    void testToCollectionModel() {
        // Setup
        final List<Cidade> cidades = List.of(new Cidade(ID, NOME));

        // Configure ModelMapper.map(...).
        final CidadeModel cidadeModel = new CidadeModel();
        cidadeModel.setId(ID);
        cidadeModel.setNome(NOME);
        final EstadoModel estado = new EstadoModel();
        estado.setId(ID);
        estado.setNome(NOME);
        cidadeModel.setEstado(estado);
        when(mockModelMapper.map(new Cidade(ID, NOME), CidadeModel.class)).thenReturn(cidadeModel);

        // Run the test
        final CollectionModel<CidadeModel> result = cidadeModelAssemblerUnderTest.toCollectionModel(cidades);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testToCollectionModel_ModelMapperThrowsConfigurationException() {
        // Setup
        final List<Cidade> cidades = List.of(new Cidade(ID, NOME));
        when(mockModelMapper.map(new Cidade(ID, NOME), CidadeModel.class)).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> cidadeModelAssemblerUnderTest.toCollectionModel(cidades))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToCollectionModel_ModelMapperThrowsMappingException() {
        // Setup
        final List<Cidade> cidades = List.of(new Cidade(ID, NOME));
        when(mockModelMapper.map(new Cidade(ID, NOME), CidadeModel.class)).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> cidadeModelAssemblerUnderTest.toCollectionModel(cidades))
                .isInstanceOf(MappingException.class);
    }
}
