package com.nice.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ItemPedidoPK implements Serializable {
	
	private static final long serialVersionUID = 1519217061318802218L;

	@ManyToOne
	@JoinColumn(name = "IDPedido", referencedColumnName = "IDPedido")
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name = "IDProduto", referencedColumnName = "IDProduto")
	private Produto produto;
	
}
