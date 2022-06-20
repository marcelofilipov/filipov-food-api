package com.thefilipov.food.domain.model;

import com.thefilipov.food.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {

	public Cidade(Long id, @NotBlank String nome) {
		this.id = id;
		this.nome = nome;
	}

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String nome;

	@Valid
	@ConvertGroup(to = Groups.EstadoId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "estado_id", nullable = false)
	private Estado estado;

}
