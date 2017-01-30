package br.com.sistemaxm.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;

import br.com.sistemaxm.entidades.Despesa;
import br.com.sistemaxm.entidades.Receita;

public class ReceitaRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	EntityManager manager;

	public ReceitaRepository(EntityManager manager) {
		// TODO Auto-generated constructor stub
		this.manager = manager;
	}

	public void salvar(Receita receita) {
		this.manager.persist(receita);
	}
	
	public void excluir(Receita receita) {
		this.manager.remove(receita);
	}
	
	public void editar(Receita receita) {
		this.manager.merge(receita);
	}
	
	public List<Receita> todos() {
		TypedQuery<Receita> query = manager.createQuery("from Receita", Receita.class);
		return query.getResultList();
	}
}
