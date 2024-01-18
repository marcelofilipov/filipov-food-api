package com.thefilipov.food.domain.repository;

import com.thefilipov.food.domain.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);

}
