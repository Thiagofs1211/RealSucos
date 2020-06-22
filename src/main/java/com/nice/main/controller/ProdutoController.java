package com.nice.main.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nice.model.Produto;
import com.nice.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	ProdutoService service = new ProdutoService();
	
	@CrossOrigin(origins = "*")
	@GetMapping()
	public ResponseEntity<List<Produto>> listarProdutos(){
		List<Produto> produtos = service.listarProdutos();
		
		if(produtos == null || produtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/comercio")
	public ResponseEntity<List<Produto>> listarProdutosComercio(){
		List<Produto> produtos = service.listarProdutosComercio();
		
		if(produtos == null || produtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/avulso")
	public ResponseEntity<List<Produto>> listarProdutosAvulso(){
		List<Produto> produtos = service.listarProdutosAvulsos();
		
		if(produtos == null || produtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/cadastrar")
	public ResponseEntity<Object> criarProduto(@Valid @RequestBody Produto produto){
		service.cadastrarProduto(produto);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@DeleteMapping("/excluir")
	public ResponseEntity<Object> excluirProduto(@Valid @RequestBody Produto produto){
		Produto oldProduto = service.procurarProduto(produto.getIdProduto());
		
		if(oldProduto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		service.removerProduto(oldProduto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PutMapping("/alterar")
	public ResponseEntity<Object> alterarProduto(@Valid @RequestBody Produto produto){
		Produto oldProduto = service.procurarProduto(produto.getIdProduto());
		
		if(oldProduto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		service.editarProduto(produto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
