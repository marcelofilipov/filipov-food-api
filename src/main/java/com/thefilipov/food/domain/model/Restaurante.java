package com.thefilipov.food.domain.model;

import com.thefilipov.food.core.validation.ValueZeroIncludeDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ValueZeroIncludeDescription(valueField = "taxaFrete", descriptionField = "nome", descriptionMandatory = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	@ManyToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	private Boolean ativo = Boolean.TRUE;

	private Boolean aberto = Boolean.FALSE;
	
	@Embedded
	private Endereco endereco;

	@ManyToMany //(fetch = FetchType.EAGER)
	@JoinTable(name = "restaurante_forma_pagamento",
		joinColumns = @JoinColumn(name = "restaurante_id"),
		inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>();

	@OneToMany //(fetch = FetchType.EAGER)
	@JoinTable(name = "restaurante_usuario_responsavel",
		joinColumns = @JoinColumn(name = "restaurante_id"),
		inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> responsaveis = new HashSet<>();

	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}

	public void abrir() {
		setAberto(true);
	}

	public void fechar() {
		setAberto(false);
	}

	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}

	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}

	public boolean removerResponsavel(Usuario usuario) {
		return getResponsaveis().remove(usuario);
	}

	public boolean adicionarResponsavel(Usuario usuario) {
		return getResponsaveis().add(usuario);
	}

	public boolean aceitaFormaPagto(FormaPagamento formaPagamento) {
		return getFormasPagamento().contains(formaPagamento);
	}

	public boolean naoAceitaFormaPagto(FormaPagamento formaPagamento) {
		return !aceitaFormaPagto(formaPagamento);
	}

	public boolean isAberto() {
		return this.aberto;
	}

	public boolean isFechado() {
		return !isAberto();
	}

	public boolean isInativo() {
		return !isAtivo();
	}

	public boolean isAtivo() {
		return this.ativo;
	}

	public boolean aberturaPermitida() {
		return isAtivo() && isFechado();
	}

	public boolean ativacaoPermitida() {
		return isInativo();
	}

	public boolean inativacaoPermitida() {
		return isAtivo();
	}

	public boolean fechamentoPermitido() {
		return isAberto();
	}
}
