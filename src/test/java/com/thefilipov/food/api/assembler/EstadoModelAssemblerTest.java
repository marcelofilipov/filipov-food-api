package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.model.EstadoModel;
import com.thefilipov.food.domain.model.Estado;
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
class EstadoModelAssemblerTest {

    public static final long ID = 0L;
    public static final String NOME = "São Paulo";
    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private EstadoModelAssembler estadoModelAssemblerUnderTest;

    @Test
    void testToModel() {
        // Setup
        final Estado estado = new Estado(ID, NOME);

        // Configure ModelMapper.map(...).
        final EstadoModel estadoModel = new EstadoModel();
        estadoModel.setId(ID);
        estadoModel.setNome(NOME);
        when(mockModelMapper.map(new Estado(ID, NOME), EstadoModel.class)).thenReturn(estadoModel);

        // Run the test
        final EstadoModel result = estadoModelAssemblerUnderTest.toModel(estado);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testToModel_ModelMapperThrowsConfigurationException() {
        // Setup
        final Estado estado = new Estado(ID, NOME);
        when(mockModelMapper.map(new Estado(ID, NOME), EstadoModel.class)).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> estadoModelAssemblerUnderTest.toModel(estado))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToModel_ModelMapperThrowsMappingException() {
        // Setup
        final Estado estado = new Estado(ID, NOME);
        when(mockModelMapper.map(new Estado(ID, NOME), EstadoModel.class)).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> estadoModelAssemblerUnderTest.toModel(estado)).isInstanceOf(MappingException.class);
    }

    @Test
    void testToCollectionModel() {
        // Setup
        final List<Estado> estados = List.of(new Estado(ID, NOME));

        // Configure ModelMapper.map(...).
        final EstadoModel estadoModel = new EstadoModel();
        estadoModel.setId(ID);
        estadoModel.setNome(NOME);
        when(mockModelMapper.map(new Estado(ID, NOME), EstadoModel.class)).thenReturn(estadoModel);

        // Run the test
        final CollectionModel<EstadoModel> result = estadoModelAssemblerUnderTest.toCollectionModel(estados);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testToCollectionModel_ModelMapperThrowsConfigurationException() {
        // Setup
        final List<Estado> estados = List.of(new Estado(ID, NOME));
        when(mockModelMapper.map(new Estado(ID, NOME), EstadoModel.class)).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> estadoModelAssemblerUnderTest.toCollectionModel(estados))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToCollectionModel_ModelMapperThrowsMappingException() {
        // Setup
        final List<Estado> estados = List.of(new Estado(ID, NOME));
        when(mockModelMapper.map(new Estado(ID, NOME), EstadoModel.class)).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> estadoModelAssemblerUnderTest.toCollectionModel(estados))
                .isInstanceOf(MappingException.class);
    }
}
