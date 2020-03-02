package com.nice.main.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nice.model.Cliente;
import com.nice.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	ClienteService service = new ClienteService();
	
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
	public List<Cliente> listar(){
		List<Cliente> clientes = service.listarClientes();
		
//		for(Cliente c : clientes) {
		//c.setDataCadastro(null);
//		}
		
		if(clientes == null || clientes.isEmpty()) {
			return null;
		}
		
		return clientes;
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Cliente> procurarClienteID(@PathVariable int id){
		Cliente cliente = service.procurarCliente(id);
		
		if(cliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@GetMapping("/buscar/{nome}")
	public ResponseEntity<List<Cliente>> procurarClienteNome(@PathVariable String nome){
		List<Cliente> clientes = service.procurarClienteNomeLike(nome);
		
		if(clientes == null || clientes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<Cliente> cadastrarCliente( @Valid @RequestBody Cliente newCliente){
		Cliente cliente = service.cadastrarCliente(newCliente);
		
		if(cliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return  new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deletar/{nome}")
	public ResponseEntity<Object> removerCliente(@PathVariable String nome){
		Cliente cliente = service.procurarClienteNome(nome);
		
		if(cliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		service.removerCliente(cliente);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/alterar")
	public ResponseEntity<Object> alterarCliente(@Valid @RequestBody Cliente cliente){
		Cliente oldCliente = service.procurarCliente(cliente.getIdCliente());
		
		if(oldCliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		service.alterarCliente(cliente);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
