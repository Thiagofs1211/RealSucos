package com.nice.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "itempedido")
public class ItemPedido {

	@EmbeddedId
	private ItemPedidoPK pk;
	
	private int quantidade;
}
