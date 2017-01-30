package br.com.sistemaxm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Query;

import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.ContratoLocacao;

public class ContratoLocacaoRepository {

	EntityManager manager;

	public ContratoLocacaoRepository(EntityManager manager) {
		this.manager = manager;
	}

	public ContratoLocacaoRepository() {

	}

	public void porId(Long id) {
		this.manager.find(ContratoLocacao.class, id);
	}

	public List<ContratoLocacao> todos() {
		TypedQuery<ContratoLocacao> query = manager.createQuery("from ContratoLocacao", ContratoLocacao.class);
		return query.getResultList();
	}
	
	public Long numLocacao() {
				
			TypedQuery<Long> query = manager.createQuery("select max(c.numeroLocacao) from ContratoLocacao c", Long.class);
			Long maxNumLoc = query.getSingleResult();
		
			if (maxNumLoc==null) {
				return (long) 0;
			}
			return maxNumLoc;
	}

	public void salvar(ContratoLocacao contratoLocacao) {
		this.manager.persist(contratoLocacao);
	}

	public void editar(ContratoLocacao contratoLocacao) {
		this.manager.merge(contratoLocacao);
	}

	public void excluir(ContratoLocacao contratoLocacao) {
		this.manager.remove(contratoLocacao);
	}

}
