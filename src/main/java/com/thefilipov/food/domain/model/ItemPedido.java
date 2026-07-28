package com.thefilipov.food.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Column(name = "preco_total", nullable = false)
    private BigDecimal precoTotal;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    public void calcularPrecoTotal() {
        BigDecimal prcUnit = this.getPrecoUnitario();
        Integer qtde = this.getQuantidade();

        if (prcUnit == null) {
            prcUnit = BigDecimal.ZERO;
        }

        if (qtde == null) {
            qtde = 0;
        }

        this.setPrecoTotal(prcUnit.multiply(new BigDecimal(qtde)));
    }
}
