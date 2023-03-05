package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.model.input.FormaPagamentoInput;
import com.thefilipov.food.domain.model.FormaPagamento;
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
class FormaPagamentoInputDisassemblerTest {

    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassemblerUnderTest;

    @Test
    void testToDomainObject() {
        // Setup
        final FormaPagamentoInput formaPagamentoInput = new FormaPagamentoInput();
        formaPagamentoInput.setDescricao("descricao");

        final FormaPagamento expectedResult = new FormaPagamento(0L, "descricao");
        when(mockModelMapper.map(any(Object.class), eq(FormaPagamento.class)))
                .thenReturn(new FormaPagamento(0L, "descricao"));

        // Run the test
        final FormaPagamento result = formaPagamentoInputDisassemblerUnderTest.toDomainObject(formaPagamentoInput);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testToDomainObject_ModelMapperThrowsConfigurationException() {
        // Setup
        final FormaPagamentoInput formaPagamentoInput = new FormaPagamentoInput();
        formaPagamentoInput.setDescricao("descricao");

        when(mockModelMapper.map(any(Object.class), eq(FormaPagamento.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(
                () -> formaPagamentoInputDisassemblerUnderTest.toDomainObject(formaPagamentoInput))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToDomainObject_ModelMapperThrowsMappingException() {
        // Setup
        final FormaPagamentoInput formaPagamentoInput = new FormaPagamentoInput();
        formaPagamentoInput.setDescricao("descricao");

        when(mockModelMapper.map(any(Object.class), eq(FormaPagamento.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(
                () -> formaPagamentoInputDisassemblerUnderTest.toDomainObject(formaPagamentoInput))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testCopyToDomainObject() {
        // Setup
        final FormaPagamentoInput formaPagamentoInput = new FormaPagamentoInput();
        formaPagamentoInput.setDescricao("descricao");

        final FormaPagamento formaPagamento = new FormaPagamento(0L, "descricao");

        // Run the test
        formaPagamentoInputDisassemblerUnderTest.copyToDomainObject(formaPagamentoInput, formaPagamento);

        // Verify the results
        verify(mockModelMapper).map(any(Object.class), eq(new FormaPagamento(0L, "descricao")));
    }

    @Test
    void testCopyToDomainObject_ModelMapperThrowsConfigurationException() {
        // Setup
        final FormaPagamentoInput formaPagamentoInput = new FormaPagamentoInput();
        formaPagamentoInput.setDescricao("descricao");

        final FormaPagamento formaPagamento = new FormaPagamento(0L, "descricao");
        doThrow(ConfigurationException.class).when(mockModelMapper).map(any(Object.class),
                eq(new FormaPagamento(0L, "descricao")));

        // Run the test
        assertThatThrownBy(() -> formaPagamentoInputDisassemblerUnderTest.copyToDomainObject(formaPagamentoInput,
                formaPagamento)).isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testCopyToDomainObject_ModelMapperThrowsMappingException() {
        // Setup
        final FormaPagamentoInput formaPagamentoInput = new FormaPagamentoInput();
        formaPagamentoInput.setDescricao("descricao");

        final FormaPagamento formaPagamento = new FormaPagamento(0L, "descricao");
        doThrow(MappingException.class).when(mockModelMapper).map(any(Object.class),
                eq(new FormaPagamento(0L, "descricao")));

        // Run the test
        assertThatThrownBy(() -> formaPagamentoInputDisassemblerUnderTest.copyToDomainObject(formaPagamentoInput,
                formaPagamento)).isInstanceOf(MappingException.class);
    }
}
