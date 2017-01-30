package br.com.sistemaxm.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.Contrato;

public class ContratoRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	EntityManager manager;

	public ContratoRepository(EntityManager manager) {
		this.manager = manager;
	}

	public Contrato porId(long codigo) {
		return this.manager.find(Contrato.class, codigo);
	}
	
	public void salvar(Contrato contrato) {
		this.manager.persist(contrato);
	}
	
	public void editar(Contrato contrato) {
		this.manager.merge(contrato);
	}
	
	public void excluir(Contrato contrato) {
		this.manager.remove(contrato);
	}
	
	public List<Contrato> todos() {
		TypedQuery<Contrato> query = manager.createQuery("from Contrato", Contrato.class);
		return query.getResultList();
	}
}
