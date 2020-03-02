package com.nice.main.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nice.model.ItemPedido;
import com.nice.model.PedidoData;
import com.nice.service.PedidoService;

@RestController
public class PedidoController {
	
	PedidoService service = new PedidoService();
	
	@RequestMapping(value = "/pedido/criar", method = RequestMethod.POST)
	public ResponseEntity<Object> inserirPedido(@Valid @RequestBody PedidoData pedido){
		service.inserirPedido(pedido.getItemPedido(), pedido.getPedido());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pedido/listar", method = RequestMethod.GET)
	public ResponseEntity<List<PedidoData>> listarPedidos(){
		List<PedidoData> pedidos = service.listarPedidos();
		
		if(pedidos == null || pedidos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<PedidoData>>(pedidos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pedido/listar/cliente/{idCliente}", method = RequestMethod.GET)
	public ResponseEntity<List<PedidoData>> listarPedidosCliente(@PathVariable int idCliente) {
		List<PedidoData> pedidos = service.listarPedidosCliente(idCliente);
		
		if(pedidos == null || pedidos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<PedidoData>>(pedidos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pedido/procurar/{idPedido}", method = RequestMethod.GET)
	public ResponseEntity<PedidoData> ProcurarPedido(@PathVariable int idPedido){
		PedidoData pedido = service.recuperaPedido(idPedido);
		
		if(pedido == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<PedidoData>(pedido, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pedido/listar/datas/{dataInicio}/{dataFim}", method = RequestMethod.GET)
	public ResponseEntity<List<PedidoData>> listarPedidosEntreDatas(@PathVariable(value = "dataInicio") Date dataInicio,
			@PathVariable(value = "dataFim") Date dataFim){
		List<PedidoData> pedidos = service.listarPedidosData(dataInicio, dataFim);
		
		if(pedidos == null || pedidos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<PedidoData>>(pedidos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pedido/editar", method = RequestMethod.PUT)
	public ResponseEntity<Object> editarPedido(@RequestBody PedidoData pedidoData){
		service.editarPedido(pedidoData);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pedido/excluir", method = RequestMethod.DELETE)
	public ResponseEntity<Object> excluirPedido(@RequestBody PedidoData pedidoData){
		int result = service.excluirPedido(pedidoData);
		
		if(result == 1) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pedido/excluir/item", method = RequestMethod.DELETE)
	public ResponseEntity<Object> excluirItemPedido(@RequestBody ItemPedido item){
		int result = service.excluirItemPedido(item);
		
		if(result == 1) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
