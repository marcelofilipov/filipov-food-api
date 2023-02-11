package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.model.input.UsuarioInput;
import com.thefilipov.food.domain.model.Grupo;
import com.thefilipov.food.domain.model.Permissao;
import com.thefilipov.food.domain.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioInputDisassemblerTest {

    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private UsuarioInputDisassembler usuarioInputDisassemblerUnderTest;

    @Test
    void testToDomainObject() {
        // Setup
        final UsuarioInput usuarioInput = new UsuarioInput();
        usuarioInput.setNome("nome");
        usuarioInput.setEmail("email");

        final Grupo grupo = new Grupo();
        grupo.setId(0L);
        grupo.setNome("nome");
        final Permissao permissao = new Permissao();
        permissao.setId(0L);
        permissao.setNome("nome");
        permissao.setDescricao("descricao");
        grupo.setPermissoes(Set.of(permissao));
        final Usuario expectedResult = new Usuario(0L, "nome", "email", "senha",
                OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(grupo));

        // Configure ModelMapper.map(...).
        final Grupo grupo1 = new Grupo();
        grupo1.setId(0L);
        grupo1.setNome("nome");
        final Permissao permissao1 = new Permissao();
        permissao1.setId(0L);
        permissao1.setNome("nome");
        permissao1.setDescricao("descricao");
        grupo1.setPermissoes(Set.of(permissao1));
        final Usuario usuario = new Usuario(0L, "nome", "email", "senha",
                OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(grupo1));
        when(mockModelMapper.map(any(Object.class), eq(Usuario.class))).thenReturn(usuario);

        // Run the test
        final Usuario result = usuarioInputDisassemblerUnderTest.toDomainObject(usuarioInput);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testToDomainObject_ModelMapperThrowsConfigurationException() {
        // Setup
        final UsuarioInput usuarioInput = new UsuarioInput();
        usuarioInput.setNome("nome");
        usuarioInput.setEmail("email");

        when(mockModelMapper.map(any(Object.class), eq(Usuario.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> usuarioInputDisassemblerUnderTest.toDomainObject(usuarioInput))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToDomainObject_ModelMapperThrowsMappingException() {
        // Setup
        final UsuarioInput usuarioInput = new UsuarioInput();
        usuarioInput.setNome("nome");
        usuarioInput.setEmail("email");

        when(mockModelMapper.map(any(Object.class), eq(Usuario.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> usuarioInputDisassemblerUnderTest.toDomainObject(usuarioInput))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testCopyToDomainObject() {
        // Setup
        final UsuarioInput usuarioInput = new UsuarioInput();
        usuarioInput.setNome("nome");
        usuarioInput.setEmail("email");

        final Grupo grupo = new Grupo();
        grupo.setId(0L);
        grupo.setNome("nome");
        final Permissao permissao = new Permissao();
        permissao.setId(0L);
        permissao.setNome("nome");
        permissao.setDescricao("descricao");
        grupo.setPermissoes(Set.of(permissao));
        final Usuario usuario = new Usuario(0L, "nome", "email", "senha",
                OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(grupo));

        // Run the test
        usuarioInputDisassemblerUnderTest.copyToDomainObject(usuarioInput, usuario);

        // Verify the results
        verify(mockModelMapper).map(any(Object.class), eq(new Usuario(0L, "nome", "email", "senha",
                OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(new Grupo()))));
    }

    @Test
    void testCopyToDomainObject_ModelMapperThrowsConfigurationException() {
        // Setup
        final UsuarioInput usuarioInput = new UsuarioInput();
        usuarioInput.setNome("nome");
        usuarioInput.setEmail("email");

        final Grupo grupo = new Grupo();
        grupo.setId(0L);
        grupo.setNome("nome");
        final Permissao permissao = new Permissao();
        permissao.setId(0L);
        permissao.setNome("nome");
        permissao.setDescricao("descricao");
        grupo.setPermissoes(Set.of(permissao));
        final Usuario usuario = new Usuario(0L, "nome", "email", "senha",
                OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(grupo));
        doThrow(ConfigurationException.class).when(mockModelMapper).map(any(Object.class),
                eq(new Usuario(0L, "nome", "email", "senha",
                        OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC),
                        Set.of(new Grupo()))));

        // Run the test
        assertThatThrownBy(
                () -> usuarioInputDisassemblerUnderTest.copyToDomainObject(usuarioInput, usuario))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testCopyToDomainObject_ModelMapperThrowsMappingException() {
        // Setup
        final UsuarioInput usuarioInput = new UsuarioInput();
        usuarioInput.setNome("nome");
        usuarioInput.setEmail("email");

        final Grupo grupo = new Grupo();
        grupo.setId(0L);
        grupo.setNome("nome");
        final Permissao permissao = new Permissao();
        permissao.setId(0L);
        permissao.setNome("nome");
        permissao.setDescricao("descricao");
        grupo.setPermissoes(Set.of(permissao));
        final Usuario usuario = new Usuario(0L, "nome", "email", "senha",
                OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(grupo));
        doThrow(MappingException.class).when(mockModelMapper).map(any(Object.class),
                eq(new Usuario(0L, "nome", "email", "senha",
                        OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC),
                        Set.of(new Grupo()))));

        // Run the test
        assertThatThrownBy(
                () -> usuarioInputDisassemblerUnderTest.copyToDomainObject(usuarioInput, usuario))
                .isInstanceOf(MappingException.class);
    }
}
