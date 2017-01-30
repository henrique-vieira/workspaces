package br.com.sistemaxm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.Pessoa;

public class PessoaRepository {

	EntityManager manager;
	public PessoaRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public PessoaRepository() {
		
	}
	
	public Pessoa porId(Long id) {
		return manager.find(Pessoa.class, id);
	}
	
	public List<Pessoa> todos() {
		TypedQuery<Pessoa> pessoas = manager.createQuery("from Pessoa", Pessoa.class);
		return pessoas.getResultList();
	}
}
