package com.algaworks.comercial.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.algaworks.comercial.model.Orcamento;
import com.algaworks.comercial.model.OrcamentoItem;

public class OrcamentosRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Orcamento guardar(Orcamento orcamento) {
		return manager.merge(orcamento);
	}
	
	public void remover(Orcamento orcamento){
		this.manager.remove(orcamento);
	}
	
	public List<Orcamento> todos(){
		TypedQuery<Orcamento> query = manager.createQuery("from Orcamento", Orcamento.class);
		return query.getResultList();
	}
}