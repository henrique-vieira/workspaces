package br.com.sistemaxm.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.AbastecimentoSemJornada;

public class AbastecimentoSemJornadaRepository implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	EntityManager manager;
	
	public AbastecimentoSemJornadaRepository(EntityManager manager) {
		this.manager = manager;
	}

	public void salvar(AbastecimentoSemJornada abastecimentoSemJornada) {
		this.manager.persist(abastecimentoSemJornada);
	}
	
	public void excluir(AbastecimentoSemJornada abastecimentoSemJornada) {
		this.manager.remove(abastecimentoSemJornada);
	}
	
	public void editar(AbastecimentoSemJornada abastecimentoSemJornada) {
		this.manager.merge(abastecimentoSemJornada);
	}
	
	public List<AbastecimentoSemJornada> todos() {
		TypedQuery<AbastecimentoSemJornada> query = manager.createQuery("from AbastecimentoSemJornada", AbastecimentoSemJornada.class);
		return query.getResultList();
	}
}
