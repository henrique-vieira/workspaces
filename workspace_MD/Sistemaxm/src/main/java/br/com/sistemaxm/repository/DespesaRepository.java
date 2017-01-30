package br.com.sistemaxm.repository;

import java.io.Serializable;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import br.com.sistemaxm.entidades.Despesa;

public class DespesaRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	EntityManager manager;

	public DespesaRepository(EntityManager manager) {
		// TODO Auto-generated constructor stub
		this.manager = manager;
	}

	public void salvar(Despesa despesa) {
		this.manager.persist(despesa);
	}
	
	public void excluir(Despesa despesa) {
		this.manager.remove(despesa);
	}
	
	public void editar(Despesa despesa) {
		this.manager.merge(despesa);
	}
	
	public List<Despesa> todos() {
		TypedQuery<Despesa> query = manager.createQuery("from Despesa", Despesa.class);
		return query.getResultList();
	}
}
