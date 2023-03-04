package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.model.FormaPagamentoModel;
import com.thefilipov.food.domain.model.FormaPagamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FormaPagamentoModelAssemblerTest {

    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private FormaPagamentoModelAssembler formaPagamentoModelAssemblerUnderTest;

    @Test
    void testToModel() {
        // Setup
        final FormaPagamento formaPagamento = new FormaPagamento(0L, "descricao");

        // Configure ModelMapper.map(...).
        final FormaPagamentoModel formaPagamentoModel = new FormaPagamentoModel();
        formaPagamentoModel.setId(0L);
        formaPagamentoModel.setDescricao("descricao");
        when(mockModelMapper.map(new FormaPagamento(0L, "descricao"), FormaPagamentoModel.class))
                .thenReturn(formaPagamentoModel);

        // Run the test
        final FormaPagamentoModel result = formaPagamentoModelAssemblerUnderTest.toModel(formaPagamento);

        // Verify the results
    }

    @Test
    void testToModel_ModelMapperThrowsConfigurationException() {
        // Setup
        final FormaPagamento formaPagamento = new FormaPagamento(0L, "descricao");
        when(mockModelMapper.map(new FormaPagamento(0L, "descricao"), FormaPagamentoModel.class))
                .thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> formaPagamentoModelAssemblerUnderTest.toModel(formaPagamento))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToModel_ModelMapperThrowsMappingException() {
        // Setup
        final FormaPagamento formaPagamento = new FormaPagamento(0L, "descricao");
        when(mockModelMapper.map(new FormaPagamento(0L, "descricao"), FormaPagamentoModel.class))
                .thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> formaPagamentoModelAssemblerUnderTest.toModel(formaPagamento))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testToCollectionModel() {
        // Setup
        final Collection<FormaPagamento> formasPagamentos = List.of(new FormaPagamento(0L, "descricao"));

        // Configure ModelMapper.map(...).
        final FormaPagamentoModel formaPagamentoModel = new FormaPagamentoModel();
        formaPagamentoModel.setId(0L);
        formaPagamentoModel.setDescricao("descricao");
        when(mockModelMapper.map(new FormaPagamento(0L, "descricao"), FormaPagamentoModel.class))
                .thenReturn(formaPagamentoModel);

        // Run the test
        final List<FormaPagamentoModel> result = formaPagamentoModelAssemblerUnderTest.toCollectionModel(
                formasPagamentos);

        // Verify the results
    }

    @Test
    void testToCollectionModel_ModelMapperThrowsConfigurationException() {
        // Setup
        final Collection<FormaPagamento> formasPagamentos = List.of(new FormaPagamento(0L, "descricao"));
        when(mockModelMapper.map(new FormaPagamento(0L, "descricao"), FormaPagamentoModel.class))
                .thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(
                () -> formaPagamentoModelAssemblerUnderTest.toCollectionModel(formasPagamentos))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToCollectionModel_ModelMapperThrowsMappingException() {
        // Setup
        final Collection<FormaPagamento> formasPagamentos = List.of(new FormaPagamento(0L, "descricao"));
        when(mockModelMapper.map(new FormaPagamento(0L, "descricao"), FormaPagamentoModel.class))
                .thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(
                () -> formaPagamentoModelAssemblerUnderTest.toCollectionModel(formasPagamentos))
                .isInstanceOf(MappingException.class);
    }
}
