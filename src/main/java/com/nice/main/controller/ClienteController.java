package com.nice.main.controller;

import com.nice.model.Cliente;
import com.nice.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	ClienteService service = new ClienteService();
	
	@CrossOrigin(origins = "*")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
	public List<Cliente> listar(){
		List<Cliente> clientes = service.listarClientes();
		
		if(clientes == null || clientes.isEmpty()) {
			return null;
		}
		
		return clientes;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/buscar/id/{id}")
	public ResponseEntity<Cliente> procurarClienteID(@PathVariable int id){
		Cliente cliente = service.procurarCliente(id);
		
		if(cliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/buscar/{nome}")
	public ResponseEntity<List<Cliente>> procurarClienteNome(@PathVariable String nome){
		List<Cliente> clientes = service.procurarClienteNomeLike(nome);
		
		if(clientes == null || clientes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/cadastro")
	public ResponseEntity<Cliente> cadastrarCliente( @Valid @RequestBody Cliente newCliente){
		Cliente cliente = service.cadastrarCliente(newCliente);
		
		if(cliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return  new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@DeleteMapping("/deletar")
	public ResponseEntity<Object> removerCliente(@Valid @RequestBody Cliente cliente){
		Cliente oldcliente = service.procurarCliente(cliente.getIdCliente());
		
		if(oldcliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		service.removerCliente(oldcliente);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
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
