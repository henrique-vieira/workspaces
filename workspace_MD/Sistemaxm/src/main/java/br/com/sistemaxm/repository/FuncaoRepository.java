package br.com.sistemaxm.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.Funcao;

public class FuncaoRepository implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	EntityManager manager;
	
	public FuncaoRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public FuncaoRepository() {
		
	}
	
	public Funcao porId(Long id) {
		return this.manager.find(Funcao.class, id);
	}
	
	public void salvar(Funcao funcao) {
		this.manager.persist(funcao);
	}
	
	public void editar(Funcao funcao) {
		this.manager.merge(funcao);
	}
	
	public void excluir(Funcao funcao) {
		this.manager.remove(funcao);
	}
	
	public List<Funcao> listaFuncao() {
		TypedQuery<Funcao> query = manager.createQuery("from Funcao", Funcao.class);
		return query.getResultList();
	}
}
