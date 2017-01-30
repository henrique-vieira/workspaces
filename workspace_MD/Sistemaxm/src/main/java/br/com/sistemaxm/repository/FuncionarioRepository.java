package br.com.sistemaxm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.Funcionario;
import br.com.sistemaxm.entidades.PessoaFisica;
import br.com.sistemaxm.entidades.PessoaJuridica;

public class FuncionarioRepository {

	EntityManager manager;

	public FuncionarioRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public Funcionario porId(Long id) {
		return this.manager.find(Funcionario.class, id);
	}

	public void salvar(Funcionario funcionario) {
		this.manager.persist(funcionario);
	}

	public void editar(Funcionario funcionario) {
		this.manager.merge(funcionario);
	}

	public void excluir(Funcionario funcionario) {
		this.manager.remove(funcionario);
	}
	//PF
	public void salvar(PessoaFisica funcionario) {
		this.manager.persist(funcionario);
	}

	public void editar(PessoaFisica funcionario) {
		this.manager.merge(funcionario);
	}

	public void excluir(PessoaFisica funcionario) {
		this.manager.remove(funcionario);
	}

	public List<Funcionario> nomesFuncionarios() {
		TypedQuery<Funcionario> query = manager.createQuery("Select new Funcionario(codigo, pessoa) from  Funcionario", Funcionario.class);
		return query.getResultList();
	}

	public List<Funcionario> todosFuncionarios() {
		TypedQuery<Funcionario> query = manager.createQuery("from Funcionario",
				Funcionario.class);
		return query.getResultList();
	}
	
	public List<PessoaFisica> pessoasFisica() {
		TypedQuery<PessoaFisica> query = manager.createQuery("from PessoaFisica where codigo in (select pessoa from Funcionario)", PessoaFisica.class);
		return query.getResultList();
	}

}
