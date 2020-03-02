package com.nice.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.nice.model.Cliente;

@Service
public class ClienteService {
	
	public EntityManagerFactory entityManagerFactory;
	public EntityManager entityManager;
	
	public ClienteService() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");
		this.entityManager = entityManagerFactory.createEntityManager();
	}
	
	/**
	 * Método para retornar a lista de todos os clientes cadastrados
	 * @return List<Cliente>
	 */
	public List<Cliente> listarClientes(){
		List<Cliente> clientes = null;
		String jpql = "select c from Cliente c";
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
		try {
			clientes = typedQuery.getResultList();
		} catch(NoResultException e) {
			System.out.println("Nenhum resultado encontrado");
			return clientes;
		}
		return clientes;
	}
	
	/**
	 * Método para procurar um cliente pelo seu ID
	 * @param id the ID Cliente
	 * @return Cliente
	 */
	public Cliente procurarCliente(int id) {
		Cliente cliente = null;
		String jpql = "select c from Cliente c where c.idCliente = :id";
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
		typedQuery.setParameter("id", id);
		try {
			cliente = typedQuery.getSingleResult();
		} catch(NoResultException e) {
			System.out.println("Nenhum resultado encontrado");
			return cliente;
		}
		return cliente;
	}
	
	/**
	 * Método para recuperar a lista de clientes que tenha a substring informada
	 * @param nome the nome cliente
	 * @return List<Cliente>
	 */
	public List<Cliente> procurarClienteNomeLike(String nome){
		List<Cliente> clientes = null;
		String jpql = "select c from Cliente c where c.nome like :nome";
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
		typedQuery.setParameter("nome", "%" + nome + "%");
		try {
			clientes = typedQuery.getResultList();
		} catch(NoResultException e) {
			System.out.println("Nenhum resultado encontrado");
			return clientes;
		}
		return clientes;
	}
	
	/**
	 * Método para retornar o cliente de acordo com o nome informado
	 * @param nome the nome cliente
	 * @return Cliente
	 */
	public Cliente procurarClienteNome(String nome) {
		Cliente cliente = null;
		String jpql = "select c from Cliente c where c.nome = :nome";
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
		typedQuery.setParameter("nome", nome);
		try {
			cliente = typedQuery.getSingleResult();
		} catch(NoResultException e) {
			System.out.println("Nenhum resultado encontrado");
			return cliente;
		}
		return cliente;
	}
	
	/**
	 * Método para cadastrar um cliente novo
	 * @param cliente the Cliente
	 * @return Cliente
	 */
	public Cliente cadastrarCliente(Cliente cliente) {
		//cliente.setDataCadastro(new Date(Calendar.getInstance().getTime().getTime()));
		cliente.setIdCliente(recuperaProximoID());
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
		
		//Verificar se o cliente foi inserido corretamente
		return procurarClienteNome(cliente.getNome());
	}
	
	/**
	 * Método para remover um cliente existente
	 * @param cliente the Cliente
	 */
	public void removerCliente(Cliente cliente) {
		entityManager.getTransaction().begin();
		entityManager.remove(cliente);
		entityManager.getTransaction().commit();
	}
	
	/**
	 * Método para recuperar o próximo ID disponível para cliente
	 * @return Integer
	 */
	public Integer recuperaProximoID() {
		String jpql = "select MAX(c.id) + 1 from Cliente c";
		TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);
		return typedQuery.getSingleResult();
	}
	
	/**
	 * Método para alterar alguma informação do cliente ja cadastrado
	 * @param cliente the Cliente
	 */
	public void alterarCliente(Cliente cliente) {
		entityManager.getTransaction().begin();
		entityManager.merge(cliente);
		entityManager.getTransaction().commit();
	}

}
