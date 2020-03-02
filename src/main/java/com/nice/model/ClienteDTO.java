package com.nice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class ClienteDTO {
	
	@Id
	@Column(name = "IDCliente")
	private int idCliente;
	
	private String nome;
	
	private String telefone;
	
	private String celular;
	
	private String bairro;
	
	private String endereco;
	
	private Date dataCadastro;
	
	@Column(name = "COMERCIO")
	private String comercio;

}
