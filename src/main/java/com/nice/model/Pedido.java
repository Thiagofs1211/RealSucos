package com.nice.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
	private int idPedido;
	
	@ManyToOne
	@JoinColumn(name = "IDCliente")
	private Cliente cliente;
	
	private float valorTotal;
	
	private float desconto;
	
	private Date dataPedido;

}
