package com.nice.main.controller;

import com.nice.model.Produto;
import com.nice.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProdutoController {

	ProdutoService service = new ProdutoService();
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listarProdutos(){
		List<Produto> produtos = service.listarProdutos();
		
		if(produtos == null || produtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/produtos/comercio", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listarProdutosComercio(){
		List<Produto> produtos = service.listarProdutosComercio();
		
		if(produtos == null || produtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/produtos/avulso", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listarProdutosAvulso(){
		List<Produto> produtos = service.listarProdutosAvulsos();
		
		if(produtos == null || produtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/produtos/cadastrar", method = RequestMethod.POST)
	public ResponseEntity<Object> criarProduto(@Valid @RequestBody Produto produto){
		service.cadastrarProduto(produto);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/produtos/excluir", method = RequestMethod.DELETE)
	public ResponseEntity<Object> excluirProduto(@Valid @RequestBody Produto produto){
		Produto oldProduto = service.procurarProduto(produto.getIdProduto());
		
		if(oldProduto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		service.removerProduto(oldProduto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/produtos/alterar", method = RequestMethod.PUT)
	public ResponseEntity<Object> alterarProduto(@Valid @RequestBody Produto produto){
		Produto oldProduto = service.procurarProduto(produto.getIdProduto());
		
		if(oldProduto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		service.editarProduto(produto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
