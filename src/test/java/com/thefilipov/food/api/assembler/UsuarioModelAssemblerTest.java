package com.thefilipov.food.api.assembler;

import com.thefilipov.food.api.model.UsuarioModel;
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
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioModelAssemblerTest {

    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private UsuarioModelAssembler usuarioModelAssemblerUnderTest;

    @Test
    void testToModel() {
        // Setup
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

        // Configure ModelMapper.map(...).
        final UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setId(0L);
        usuarioModel.setNome("nome");
        usuarioModel.setEmail("email");
        when(mockModelMapper.map(new Usuario(0L, "nome", "email", "senha",
                        OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(new Grupo())),
                UsuarioModel.class)).thenReturn(usuarioModel);

        // Run the test
        final UsuarioModel result = usuarioModelAssemblerUnderTest.toModel(usuario);

        // Verify the results
    }

    @Test
    void testToModel_ModelMapperThrowsConfigurationException() {
        // Setup
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
        when(mockModelMapper.map(new Usuario(0L, "nome", "email", "senha",
                        OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(new Grupo())),
                UsuarioModel.class)).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> usuarioModelAssemblerUnderTest.toModel(usuario))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToModel_ModelMapperThrowsMappingException() {
        // Setup
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
        when(mockModelMapper.map(new Usuario(0L, "nome", "email", "senha",
                        OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(new Grupo())),
                UsuarioModel.class)).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> usuarioModelAssemblerUnderTest.toModel(usuario)).isInstanceOf(MappingException.class);
    }

    @Test
    void testToCollectionModel() {
        // Setup
        final Grupo grupo = new Grupo();
        grupo.setId(0L);
        grupo.setNome("nome");
        final Permissao permissao = new Permissao();
        permissao.setId(0L);
        permissao.setNome("nome");
        permissao.setDescricao("descricao");
        grupo.setPermissoes(Set.of(permissao));
        final Collection<Usuario> usuarios = List.of(new Usuario(0L, "nome", "email", "senha",
                OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(grupo)));

        // Configure ModelMapper.map(...).
        final UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setId(0L);
        usuarioModel.setNome("nome");
        usuarioModel.setEmail("email");
        when(mockModelMapper.map(new Usuario(0L, "nome", "email", "senha",
                        OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(new Grupo())),
                UsuarioModel.class)).thenReturn(usuarioModel);

        // Run the test
        final List<UsuarioModel> result = usuarioModelAssemblerUnderTest.toCollectionModel(usuarios);

        // Verify the results
    }

    @Test
    void testToCollectionModel_ModelMapperThrowsConfigurationException() {
        // Setup
        final Grupo grupo = new Grupo();
        grupo.setId(0L);
        grupo.setNome("nome");
        final Permissao permissao = new Permissao();
        permissao.setId(0L);
        permissao.setNome("nome");
        permissao.setDescricao("descricao");
        grupo.setPermissoes(Set.of(permissao));
        final Collection<Usuario> usuarios = List.of(new Usuario(0L, "nome", "email", "senha",
                OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(grupo)));
        when(mockModelMapper.map(new Usuario(0L, "nome", "email", "senha",
                        OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(new Grupo())),
                UsuarioModel.class)).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> usuarioModelAssemblerUnderTest.toCollectionModel(usuarios))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testToCollectionModel_ModelMapperThrowsMappingException() {
        // Setup
        final Grupo grupo = new Grupo();
        grupo.setId(0L);
        grupo.setNome("nome");
        final Permissao permissao = new Permissao();
        permissao.setId(0L);
        permissao.setNome("nome");
        permissao.setDescricao("descricao");
        grupo.setPermissoes(Set.of(permissao));
        final Collection<Usuario> usuarios = List.of(new Usuario(0L, "nome", "email", "senha",
                OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(grupo)));
        when(mockModelMapper.map(new Usuario(0L, "nome", "email", "senha",
                        OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0), ZoneOffset.UTC), Set.of(new Grupo())),
                UsuarioModel.class)).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> usuarioModelAssemblerUnderTest.toCollectionModel(usuarios))
                .isInstanceOf(MappingException.class);
    }
}
