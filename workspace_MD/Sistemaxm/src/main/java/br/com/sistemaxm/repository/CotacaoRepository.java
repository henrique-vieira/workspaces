package br.com.sistemaxm.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.CotacaoPecas;

public class CotacaoRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	EntityManager manager = JpaUtil.getEntityManager();
	
	public CotacaoRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public CotacaoRepository() {
		
	}
	
	public CotacaoPecas porId(Long id) {
		return this.manager.find(CotacaoPecas.class, id);
	}
	
	public List<CotacaoPecas> todos() {
		TypedQuery<CotacaoPecas> query = manager.createQuery("from CotacaoPecas", CotacaoPecas.class);
		return query.getResultList();
	}
	
	public void adicionar(CotacaoPecas cotacaoPecas) {
		this.manager.persist(cotacaoPecas);
	}
	
	public void editar(CotacaoPecas cotacaoPecas) {
		this.manager.merge(cotacaoPecas);
	}
	
	public void excluir(CotacaoPecas cotacaoPecas) {
		this.manager.remove(cotacaoPecas);
	}
}
