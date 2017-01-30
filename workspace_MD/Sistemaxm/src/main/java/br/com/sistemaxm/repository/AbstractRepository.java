package br.com.sistemaxm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class AbstractRepository {

	EntityManager manager;
	
	public AbstractRepository(EntityManager manager) {
		this.manager = manager;
	}

	public Object porId(Long id) {
		return this.manager.find(Object.class, id);
	}
	
	private List<Object> todos() {
		TypedQuery<Object> query = manager.createQuery("from Object", Object.class);
		return query.getResultList();
	}
}
