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

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FormaPagamentoModelAssemblerTest {

    private static final long ID = 1L;
    private static final String DESCRIPTION = "Dinheiro";
    private static final OffsetDateTime NOW = OffsetDateTime.now();

    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private FormaPagamentoModelAssembler formaPagamentoModelAssemblerUnderTest;

    @Test
    void testToModel() {
        // Setup
        final FormaPagamento formaPagamento = new FormaPagamento(ID, DESCRIPTION, NOW);

        // Configure ModelMapper.map(...).
        final FormaPagamentoModel formaPagamentoModel = new FormaPagamentoModel();
        formaPagamentoModel.setId(ID);
        formaPagamentoModel.setDescricao(DESCRIPTION);
        when(mockModelMapper.map(new FormaPagamento(ID, DESCRIPTION, NOW), FormaPagamentoModel.class))
                .thenReturn(formaPagamentoModel);

        // Run the test
        final FormaPagamentoModel result = formaPagamentoModelAssemblerUnderTest.toModel(formaPagamento);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testToModel_ModelMapperThrowsConfigurationException() {
        // Setup
        final FormaPagamento formaPagamento = new FormaPagamento(ID, DESCRIPTION, NOW);
        when(mockModelMapper.map(new FormaPagamento(ID, DESCRIPTION, NOW), FormaPagamentoModel.class))
                .thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> formaPagamentoModelAssemblerUnderTest.toModel(formaPagamento))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToModel_ModelMapperThrowsMappingException() {
        // Setup
        final FormaPagamento formaPagamento = new FormaPagamento(ID, DESCRIPTION, NOW);
        when(mockModelMapper.map(new FormaPagamento(ID, DESCRIPTION, NOW), FormaPagamentoModel.class))
                .thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> formaPagamentoModelAssemblerUnderTest.toModel(formaPagamento))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testToCollectionModel() {
        // Setup
        final Collection<FormaPagamento> formasPagamentos = List.of(new FormaPagamento(ID, DESCRIPTION, NOW));

        // Configure ModelMapper.map(...).
        final FormaPagamentoModel formaPagamentoModel = new FormaPagamentoModel();
        formaPagamentoModel.setId(ID);
        formaPagamentoModel.setDescricao(DESCRIPTION);
        when(mockModelMapper.map(new FormaPagamento(ID, DESCRIPTION, NOW), FormaPagamentoModel.class))
                .thenReturn(formaPagamentoModel);

        // Run the test
        final List<FormaPagamentoModel> result = formaPagamentoModelAssemblerUnderTest.toCollectionModel(
                formasPagamentos);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testToCollectionModel_ModelMapperThrowsConfigurationException() {
        // Setup
        final Collection<FormaPagamento> formasPagamentos = List.of(new FormaPagamento(ID, DESCRIPTION, NOW));
        when(mockModelMapper.map(new FormaPagamento(ID, DESCRIPTION, NOW), FormaPagamentoModel.class))
                .thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(
                () -> formaPagamentoModelAssemblerUnderTest.toCollectionModel(formasPagamentos))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToCollectionModel_ModelMapperThrowsMappingException() {
        // Setup
        final Collection<FormaPagamento> formasPagamentos = List.of(new FormaPagamento(ID, DESCRIPTION, NOW));
        when(mockModelMapper.map(new FormaPagamento(ID, DESCRIPTION, NOW), FormaPagamentoModel.class))
                .thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(
                () -> formaPagamentoModelAssemblerUnderTest.toCollectionModel(formasPagamentos))
                .isInstanceOf(MappingException.class);
    }
}
