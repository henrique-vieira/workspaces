package com.algaworks.comercial.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import com.algaworks.comercial.model.Cliente;

public class ClientesRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	private List<Cliente> clientes = new ArrayList<>();
	
	public ClientesRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public ClientesRepository() {
		
	}
	
	public Cliente porid(Long id) {
		return this.manager.find(Cliente.class, id);
	}
	
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<Cliente> buscarTodos() {
		
			/*CriteriaQuery consulta = manager.getCriteriaBuilder().createQuery();
			consulta.select(consulta.from(Cliente.class));
			return manager.createQuery(consulta).getResultList();*/
		return manager.createNamedQuery("Select * from Cliente", Cliente.class).getResultList();
		
	}
	
	public List<Cliente> nomesCliente() {
		TypedQuery<Cliente> query = manager.createQuery("Select new Cliente(c.id, c.nome) from Cliente c ", Cliente.class);
		return query.getResultList();
	}
	
	public void guardar(Cliente cliente) {
		this.manager.persist(cliente);
	}
	
	public void editar(Cliente cliente) {
		this.manager.merge(cliente);
	}
	
	public void remover(Cliente cliente) {
		this.manager.remove(cliente);
	}
	

}