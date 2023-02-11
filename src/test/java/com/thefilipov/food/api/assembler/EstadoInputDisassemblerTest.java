package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.model.input.EstadoInput;
import com.thefilipov.food.domain.model.Estado;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadoInputDisassemblerTest {

    public static final long ID = 0L;
    public static final String NOME = "São Paulo";
    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private EstadoInputDisassembler estadoInputDisassemblerUnderTest;

    @Test
    void testToDomainObject() {
        // Setup
        final EstadoInput estadoInput = new EstadoInput();
        estadoInput.setNome(NOME);

        final Estado expectedResult = new Estado(ID, NOME);
        when(mockModelMapper.map(any(Object.class), eq(Estado.class))).thenReturn(new Estado(ID, NOME));

        // Run the test
        final Estado result = estadoInputDisassemblerUnderTest.toDomainObject(estadoInput);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testToDomainObject_ModelMapperThrowsConfigurationException() {
        // Setup
        final EstadoInput estadoInput = new EstadoInput();
        estadoInput.setNome(NOME);

        when(mockModelMapper.map(any(Object.class), eq(Estado.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> estadoInputDisassemblerUnderTest.toDomainObject(estadoInput))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToDomainObject_ModelMapperThrowsMappingException() {
        // Setup
        final EstadoInput estadoInput = new EstadoInput();
        estadoInput.setNome(NOME);

        when(mockModelMapper.map(any(Object.class), eq(Estado.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> estadoInputDisassemblerUnderTest.toDomainObject(estadoInput))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testCopyToDomainObject() {
        // Setup
        final EstadoInput estadoInput = new EstadoInput();
        estadoInput.setNome(NOME);

        final Estado estado = new Estado(ID, NOME);

        // Run the test
        estadoInputDisassemblerUnderTest.copyToDomainObject(estadoInput, estado);

        // Verify the results
        verify(mockModelMapper).map(any(Object.class), eq(new Estado(ID, NOME)));
    }

    @Test
    void testCopyToDomainObject_ModelMapperThrowsConfigurationException() {
        // Setup
        final EstadoInput estadoInput = new EstadoInput();
        estadoInput.setNome(NOME);

        final Estado estado = new Estado(ID, NOME);
        doThrow(ConfigurationException.class).when(mockModelMapper).map(any(Object.class), eq(new Estado(ID, NOME)));

        // Run the test
        assertThatThrownBy(() -> estadoInputDisassemblerUnderTest.copyToDomainObject(estadoInput, estado))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testCopyToDomainObject_ModelMapperThrowsMappingException() {
        // Setup
        final EstadoInput estadoInput = new EstadoInput();
        estadoInput.setNome(NOME);

        final Estado estado = new Estado(ID, NOME);
        doThrow(MappingException.class).when(mockModelMapper).map(any(Object.class), eq(new Estado(ID, NOME)));

        // Run the test
        assertThatThrownBy(() -> estadoInputDisassemblerUnderTest.copyToDomainObject(estadoInput, estado))
                .isInstanceOf(MappingException.class);
    }
}
