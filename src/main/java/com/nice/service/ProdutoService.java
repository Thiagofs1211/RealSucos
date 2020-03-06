package com.nice.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.nice.model.Produto;

@Service
public class ProdutoService {
	
	public EntityManagerFactory entityManagerFactory;
	public EntityManager entityManager;
	
	public ProdutoService() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");
		this.entityManager = entityManagerFactory.createEntityManager();
	}
	
	/**
	 * Método para listar todos os produtos cadastrados
	 * @return List<Produto>
	 */
	public List<Produto> listarProdutos(){
		List<Produto> produtos = null;
		String jpql = "select p from Produto p";
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
		try {
			produtos = typedQuery.getResultList();
		} catch(NoResultException e) {
			System.out.println("Nenhum produto encontrado");
			return null;
		}
		return produtos;
	}
	
	/**
	 * Método para listar todos os produtos vendidos para comércios
	 * @return List<Produto>
	 */
	public List<Produto> listarProdutosComercio(){
		List<Produto> produtos = null;
		String jpql = "select p from Produto p where p.comercio = :comercio";
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
		typedQuery.setParameter("comercio", "S");
		try {
			produtos = typedQuery.getResultList();
		} catch(NoResultException e) {
			System.out.println("Nenhum produto encontrado");
			return null;
		}
		return produtos;
	}
	
	/**
	 * Método para listar todos os produtos vendidos avulso
	 * @return List<Produto>
	 */
	public List<Produto> listarProdutosAvulsos(){
		List<Produto> produtos = null;
		EntityManagerFactory entityManagerFactory2 = Persistence.createEntityManagerFactory("Clientes-PU");
		EntityManager entityManager2 = entityManagerFactory2.createEntityManager();
		String jpql = "select p from Produto p where p.comercio = :comercio";
		TypedQuery<Produto> typedQuery = entityManager2.createQuery(jpql, Produto.class);
		typedQuery.setParameter("comercio", "N");
		try {
			produtos = typedQuery.getResultList();
		} catch(NoResultException e) {
			System.out.println("Nenhum produto encontrado");
			return null;
		}
		return produtos;
	}
	
	/**
	 * Método para recuperar um produto de acordo com seu ID
	 * @param id the id produto
	 * @return Produto
	 */
	public Produto procurarProduto(int id) {
		Produto produto = null;
		String jpql = "select p from Produto p where p.idProduto = :id";
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
		typedQuery.setParameter("id", id);
		try {
			produto = typedQuery.getSingleResult();
		} catch(NoResultException e) {
			System.out.println("Nenhum produto encontrado");
			return null;
		}
		return produto;
	}
	
	/**
	 * Método para cadastrar um novo produto
	 * @param produto the Produto
	 */
	public void cadastrarProduto(Produto produto) {
		produto.setIdProduto(recuperaProximoID());
		if(!entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().begin();
		}
		entityManager.persist(produto);
		entityManager.getTransaction().commit();
	}
	
	/**
	 * Método para recuperar o próximo ID de produto
	 * @return Integer
	 */
	public Integer recuperaProximoID() {
		Integer id = 1;
		String jpql = "select MAX(p.id) + 1 from Produto p";
		TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);
		try {
			id = typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return 1;
		}
		if(id == null) {
			return 1;
		}
		return id;
	}
	
	/**
	 * Método para remover um produto existente
	 * @param produto the Produto
	 */
	public void removerProduto(Produto produto) {
		entityManager.getTransaction().begin();
		entityManager.remove(produto);
		entityManager.getTransaction().commit();
	}
	
	/**
	 * Método para editar um produto existente
	 * @param produto the Produto
	 */
	public void editarProduto(Produto produto) {
		entityManager.getTransaction().begin();
		entityManager.merge(produto);
		entityManager.getTransaction().commit();
	}

}
