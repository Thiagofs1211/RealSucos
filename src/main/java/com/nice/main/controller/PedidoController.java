package com.nice.main.controller;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nice.model.ItemPedido;
import com.nice.model.PedidoData;
import com.nice.service.PedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
	
	PedidoService service = new PedidoService();
	
	@CrossOrigin(origins = "*")
	@PostMapping("/criar")
	public ResponseEntity<Object> inserirPedido(@Valid @RequestBody PedidoData pedido){
		service.inserirPedido(pedido.getItemPedido(), pedido.getPedido());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/listar")
	public ResponseEntity<List<PedidoData>> listarPedidos(){
		List<PedidoData> pedidos = service.listarPedidos();
		
		if(pedidos == null || pedidos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<PedidoData>>(pedidos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/listar/cliente")
	public ResponseEntity<List<PedidoData>> listarPedidosCliente(@RequestParam("idCliente") String idCliente, @RequestParam("dataInicio") String dataInicio, @RequestParam("dataFim") String dataFim) {
		dataInicio = dataInicio.substring(0,dataInicio.lastIndexOf("3")+1)+":00";
		dataFim = dataFim.substring(0,dataFim.lastIndexOf("3")+1)+":00";
		SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd yyyy HH:mm:ss z", Locale.US);  
		SimpleDateFormat sdf2 = new SimpleDateFormat ("yyyy-MM-dd");
		Date dataI = null;
		Date dataF = null;
		try {
			Date dt = sdf1.parse(dataInicio);
			dataInicio = sdf2.format(dt);
			dataI = sdf2.parse(dataInicio);
			
			Date dt2 = sdf1.parse(dataFim);
			dataFim = sdf2.format(dt2);
			dataF = sdf2.parse(dataFim);
					
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		List<PedidoData> pedidos = service.listarPedidosCliente(Integer.parseInt(idCliente), dataI, dataF);
		
		if(pedidos == null || pedidos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<PedidoData>>(pedidos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/procurar/{idPedido}")
	public ResponseEntity<PedidoData> ProcurarPedido(@PathVariable("idPedido") int idPedido) {
		PedidoData pedido = service.recuperaPedido(idPedido);
		
		if(pedido == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<PedidoData>(pedido, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/listar/datas")
	public ResponseEntity<List<PedidoData>> listarPedidosEntreDatas(@RequestParam(value = "dataInicio") String dataInicio,
			@RequestParam(value = "dataFim") String dataFim){
		dataInicio = dataInicio.substring(0,dataInicio.lastIndexOf("3")+1)+":00";
		dataFim = dataFim.substring(0,dataFim.lastIndexOf("3")+1)+":00";
		SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd yyyy HH:mm:ss z", Locale.US);  
		SimpleDateFormat sdf2 = new SimpleDateFormat ("yyyy-MM-dd");
		Date dataI = null;
		Date dataF = null;
		try {
			Date dt = sdf1.parse(dataInicio);
			dataInicio = sdf2.format(dt);
			dataI = sdf2.parse(dataInicio);
			
			Date dt2 = sdf1.parse(dataFim);
			dataFim = sdf2.format(dt2);
			dataF = sdf2.parse(dataFim);
					
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		List<PedidoData> pedidos = service.listarPedidosData(dataI, dataF);
		
		if(pedidos == null || pedidos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<PedidoData>>(pedidos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PutMapping("/editar")
	public ResponseEntity<Object> editarPedido(@RequestBody PedidoData pedidoData){
		service.editarPedido(pedidoData);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@DeleteMapping("/excluir")
	public ResponseEntity<Object> excluirPedido(@RequestBody PedidoData pedidoData){
		int result = service.excluirPedido(pedidoData);
		
		if(result == 1) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@DeleteMapping("/excluir/item")
	public ResponseEntity<Object> excluirItemPedido(@RequestBody ItemPedido item){
		int result = service.excluirItemPedido(item);
		
		if(result == 1) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
