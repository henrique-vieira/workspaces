package br.com.sistemaxm.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import br.com.sistemaxm.entidades.Marca;

public class MarcaRepository implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
		
	public MarcaRepository(EntityManager manager) {
		this.manager = manager;
	}
		
	public MarcaRepository() {
		
	}
	
	public Marca porId(Long id) {
		return manager.find(Marca.class, id);
	}
		
	public List<Marca> todos() {
		TypedQuery<Marca> query = manager.createQuery("from Marca", Marca.class);
		return query.getResultList();
	}
	
	
	public void adicionar(Marca marca) {
		
			this.manager.persist(marca);
					
	}
	
	public void remover(Marca marca) {
		this.manager.remove(marca);
	}
	
	public void atualizar(Marca marca) {
		this.manager.merge(marca);
	}
}
