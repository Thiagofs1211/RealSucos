package com.nice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Produto")
public class Produto {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "IDProduto")
	private int idProduto;
	
	private String nome;
	
	private String marca;
	
	private float preco;
	
	private String comercio;
	
}
