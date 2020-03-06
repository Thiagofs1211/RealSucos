package com.nice.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.nice.model.ItemPedido;
import com.nice.model.Pedido;
import com.nice.model.PedidoData;
import com.nice.model.Produto;

@Service
public class PedidoService {
	
	public EntityManagerFactory entityManagerFactory;
	public EntityManager entityManager;

	public PedidoService() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");
		this.entityManager = entityManagerFactory.createEntityManager();
	}
	
	public void inserirPedido(List<ItemPedido> itensPedido, Pedido pedido) {
		pedido.setIdPedido(recuperarProximoPedidoId());
		pedido.setDataPedido(new Date());
		
		//Inserindo o pedido
		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		
		//Inserindo os itens do pedido
		for(ItemPedido item : itensPedido) {
			item.getPk().setPedido(pedido);
			entityManager.persist(item);
		}
		entityManager.getTransaction().commit();
	}
	
	/**
	 * Método para recuperar o próximo ID disponível para Pedido
	 * @return Integer
	 */
	public Integer recuperarProximoPedidoId() {
		String jpql = "select COALESCE(MAX(p.id) + 1,1) from Pedido p";
		TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);
		return typedQuery.getSingleResult();
	}
	
	/**
	 * Método para listar todos os Pedidos
	 * @return List<PedidoData>
	 */
	public List<PedidoData> listarPedidos() {
		List<Pedido> pedidosAux = null;
		String jpql = "select p from Pedido p";
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		try {
			pedidosAux = typedQuery.getResultList();
		} catch(NoResultException e) {
			System.out.println("Nenhum pedido encontrado");
			return null;
		}
		
		List<PedidoData> pedidos = new ArrayList<>();
		
		for(Pedido pedido : pedidosAux) {
			PedidoData pedidoDataAux = new PedidoData();
			String jpql2 = "select i from ItemPedido i where i.pk.pedido.idPedido = :idPedido";
			TypedQuery<ItemPedido> typedQuery2 = entityManager.createQuery(jpql2, ItemPedido.class);
			typedQuery2.setParameter("idPedido", pedido.getIdPedido());
			pedidoDataAux.setItemPedido(typedQuery2.getResultList());
			pedidoDataAux.setPedido(pedido);
			pedidos.add(pedidoDataAux);
		}
		return pedidos;
	}
	
	/**
	 * Método para listar os pedidos de acordo com um cliente
	 * @param filtro the filtro
	 * @return List<PedidoData>
	 */
	public List<PedidoData> listarPedidosCliente(int idCliente, Date dataInicio, Date dataFim){
		List<Pedido> pedidosAux = null;
		String jpql = "select p from Pedido p where p.cliente.idCliente = :idCliente and p.dataPedido BETWEEN :dataI AND :dataF ORDER BY p.idPedido DESC";
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("idCliente", idCliente);
		typedQuery.setParameter("dataI", dataInicio);
		typedQuery.setParameter("dataF", dataFim);
		try {
			pedidosAux = typedQuery.getResultList();
		} catch(NoResultException e) {
			System.out.println("Nenhum pedido encontrado");
			return null;
		}
		
		List<PedidoData> pedidos = new ArrayList<>();
		
		for(Pedido pedido : pedidosAux) {
			PedidoData pedidoDataAux = new PedidoData();
			String jpql2 = "select i from ItemPedido i where i.pk.pedido.idPedido = :idPedido";
			TypedQuery<ItemPedido> typedQuery2 = entityManager.createQuery(jpql2, ItemPedido.class);
			typedQuery2.setParameter("idPedido", pedido.getIdPedido());
			pedidoDataAux.setItemPedido(typedQuery2.getResultList());
			pedidoDataAux.setPedido(pedido);
			pedidos.add(pedidoDataAux);
		}
		return pedidos;
	}
	
	/**
	 * Método para listar os pedidos entre uma data
	 * @param dataInicio the data inicio
	 * @param dataFim the data fim
	 * @return List<PedidoData>
	 */
	public List<PedidoData> listarPedidosData(Date dataInicio, Date dataFim){
		List<Pedido> pedidosAux = null;
		String jpql = "select p from Pedido p where p.dataPedido BETWEEN :dataI AND :dataF ORDER BY p.idPedido DESC";
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("dataI", dataInicio);
		typedQuery.setParameter("dataF", dataFim);
		try {
			pedidosAux = typedQuery.getResultList();
		} catch(NoResultException e) {
			System.out.println("Nenhum pedido encontrado");
			return null;
		}
		
		List<PedidoData> pedidos = new ArrayList<>();
		
		for(Pedido pedido : pedidosAux) {
			PedidoData pedidoDataAux = new PedidoData();
			String jpql2 = "select i from ItemPedido i where i.pk.pedido.idPedido = :idPedido";
			TypedQuery<ItemPedido> typedQuery2 = entityManager.createQuery(jpql2, ItemPedido.class);
			typedQuery2.setParameter("idPedido", pedido.getIdPedido());
			pedidoDataAux.setItemPedido(typedQuery2.getResultList());
			pedidoDataAux.setPedido(pedido);
			pedidos.add(pedidoDataAux);
		}
		return pedidos;
	}
	
	/**
	 * Métoto para recuperar um pedido especifico
	 * @param idPedido the id do pedido
	 * @return PedidoData
	 */
	public PedidoData recuperaPedido(int idPedido) {
		Pedido pedidoAux = null;
		String jpql = "select p from Pedido p where p.idPedido = :id";
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("id", idPedido);
		try {
			pedidoAux = typedQuery.getSingleResult();
		} catch(NoResultException e) {
			System.out.println("Nenhum pedido encontrado");
			return null;
		}
		
		PedidoData pedido = new PedidoData();
		
		String jpql2 = "select i from ItemPedido i where i.pk.pedido.idPedido = :idPedido";
		TypedQuery<ItemPedido> typedQuery2 = entityManager.createQuery(jpql2, ItemPedido.class);
		typedQuery2.setParameter("idPedido", idPedido);
		pedido.setItemPedido(typedQuery2.getResultList());
		pedido.setPedido(pedidoAux);
			
		return pedido;
	}
	
	/**
	 * Método para editar um pedido
	 * @param pedidoData the pedido alterado
	 */
	public void editarPedido(PedidoData pedidoData) {
		
		try {
			Pedido pedido = entityManager.find(Pedido.class, pedidoData.getPedido().getIdPedido());
			pedido.setDataPedido(pedidoData.getPedido().getDataPedido());
			pedido.setDesconto(pedidoData.getPedido().getDesconto());
			pedido.setValorTotal(pedidoData.getPedido().getValorTotal());
			
			entityManager.getTransaction().begin();
			entityManager.merge(pedido);
			
			for(ItemPedido item : pedidoData.getItemPedido()) {
				ItemPedido itemPedidoAux = entityManager.find(ItemPedido.class, item.getPk());
				itemPedidoAux.setQuantidade(item.getQuantidade());
				entityManager.merge(itemPedidoAux);
			}
			
			entityManager.getTransaction().commit();
		} catch(NoResultException e){
			System.out.println("Nenhum pedido encontrado");
		}
	}
	
	/**
	 * Método para excluir um pedido
	 * @param pedidoData the pedido data
	 * @return int
	 */
	public int excluirPedido(PedidoData pedidoData) {
		
		try {
			Pedido pedido = entityManager.find(Pedido.class, pedidoData.getPedido().getIdPedido());
			
			entityManager.getTransaction().begin();
			for(ItemPedido item : pedidoData.getItemPedido()) {
				ItemPedido itemPedidoAux = entityManager.find(ItemPedido.class, item.getPk());
				entityManager.remove(itemPedidoAux);
			}
			
			entityManager.remove(pedido);
			entityManager.getTransaction().commit();
		} catch(NoResultException e) {
			System.out.println("Nenhum pedido encontrado");
			return 1;
		}
		return 0;
	}
	
	/**
	 * Método para excluir um item do pedido
	 * @param item the item do pedido
	 * @return int
	 */
	public int excluirItemPedido(ItemPedido item) {
		
		try {
			
			ItemPedido itemAux = entityManager.find(ItemPedido.class, item.getPk());
			entityManager.getTransaction().begin();
			entityManager.remove(itemAux);
			
			//Atualizando o valor total do pedido
			Pedido pedido = entityManager.find(Pedido.class, item.getPk().getPedido().getIdPedido());
			pedido.setValorTotal(pedido.getValorTotal() - (item.getPreco() * item.getQuantidade()));
			entityManager.merge(pedido);
			entityManager.getTransaction().commit();
			
		} catch (NoResultException e) {
			System.out.println("Nenhum pedido encontrado");
			return 1;
		}
		return 0;
	}
}
