package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.model.input.CidadeInput;
import com.thefilipov.food.api.model.input.EstadoIdInput;
import com.thefilipov.food.domain.model.Cidade;
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
class CidadeInputDisassemblerTest {

    public static final long ID = 0L;
    public static final String NOME = "Santo André";
    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private CidadeInputDisassembler cidadeInputDisassemblerUnderTest;

    @Test
    void testToDomainObject() {
        // Setup
        final CidadeInput cidadeInput = new CidadeInput();
        cidadeInput.setNome(NOME);
        final EstadoIdInput estado = new EstadoIdInput();
        estado.setId(ID);
        cidadeInput.setEstado(estado);

        final Cidade expectedResult = new Cidade(ID, NOME);
        when(mockModelMapper.map(any(Object.class), eq(Cidade.class))).thenReturn(new Cidade(ID, NOME));

        // Run the test
        final Cidade result = cidadeInputDisassemblerUnderTest.toDomainObject(cidadeInput);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testToDomainObject_ModelMapperThrowsConfigurationException() {
        // Setup
        final CidadeInput cidadeInput = new CidadeInput();
        cidadeInput.setNome(NOME);
        final EstadoIdInput estado = new EstadoIdInput();
        estado.setId(ID);
        cidadeInput.setEstado(estado);

        when(mockModelMapper.map(any(Object.class), eq(Cidade.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> cidadeInputDisassemblerUnderTest.toDomainObject(cidadeInput))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToDomainObject_ModelMapperThrowsMappingException() {
        // Setup
        final CidadeInput cidadeInput = new CidadeInput();
        cidadeInput.setNome(NOME);
        final EstadoIdInput estado = new EstadoIdInput();
        estado.setId(ID);
        cidadeInput.setEstado(estado);

        when(mockModelMapper.map(any(Object.class), eq(Cidade.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> cidadeInputDisassemblerUnderTest.toDomainObject(cidadeInput))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testCopyToDomainObject() {
        // Setup
        final CidadeInput cidadeInput = new CidadeInput();
        cidadeInput.setNome(NOME);
        final EstadoIdInput estado = new EstadoIdInput();
        estado.setId(ID);
        cidadeInput.setEstado(estado);

        final Cidade cidade = new Cidade(ID, NOME);

        // Run the test
        cidadeInputDisassemblerUnderTest.copyToDomainObject(cidadeInput, cidade);

        // Verify the results
        verify(mockModelMapper).map(any(Object.class), eq(new Cidade(ID, NOME)));
    }

    @Test
    void testCopyToDomainObject_ModelMapperThrowsConfigurationException() {
        // Setup
        final CidadeInput cidadeInput = new CidadeInput();
        cidadeInput.setNome(NOME);
        final EstadoIdInput estado = new EstadoIdInput();
        estado.setId(ID);
        cidadeInput.setEstado(estado);

        final Cidade cidade = new Cidade(ID, NOME);
        doThrow(ConfigurationException.class).when(mockModelMapper).map(any(Object.class), eq(new Cidade(ID, NOME)));

        // Run the test
        assertThatThrownBy(() -> cidadeInputDisassemblerUnderTest.copyToDomainObject(cidadeInput, cidade))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testCopyToDomainObject_ModelMapperThrowsMappingException() {
        // Setup
        final CidadeInput cidadeInput = new CidadeInput();
        cidadeInput.setNome(NOME);
        final EstadoIdInput estado = new EstadoIdInput();
        estado.setId(ID);
        cidadeInput.setEstado(estado);

        final Cidade cidade = new Cidade(ID, NOME);
        doThrow(MappingException.class).when(mockModelMapper).map(any(Object.class), eq(new Cidade(ID, NOME)));

        // Run the test
        assertThatThrownBy(() -> cidadeInputDisassemblerUnderTest.copyToDomainObject(cidadeInput, cidade))
                .isInstanceOf(MappingException.class);
    }
}
