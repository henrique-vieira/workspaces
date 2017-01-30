package br.com.sistemaxm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.Servico;

public class ServicoRepository {

	EntityManager manager;
	
	public ServicoRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public ServicoRepository() {
		
	}
	
	public Servico porId(Long id) {
		return this.manager.find(Servico.class, id);
	}
	
	public void adicionar(Servico servico) {
		this.manager.persist(servico);
	}
	
	public void editar(Servico servico) {
		this.manager.merge(servico);
	}
	
	public void excluir(Servico servico) {
		this.manager.remove(servico);
	}
	
	public List<Servico> todos() {
		TypedQuery<Servico> query = manager.createQuery("from Servico", Servico.class);
		return query.getResultList();
	}
}
