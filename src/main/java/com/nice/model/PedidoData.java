package com.nice.model;

import java.util.List;

import lombok.Data;

@Data
public class PedidoData {
	
	 private Pedido pedido;
	 
	 private List<ItemPedido> itemPedido;

}
