package br.com.sistemaxm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.entidades.PessoaJuridica;

public class FornecedorRepository {

	EntityManager manager;
	
	public FornecedorRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public FornecedorRepository() {
		
	}
	
	public Fornecedor porId(Long id) {
		return this.manager.find(Fornecedor.class, id);
	}
	
	public List<PessoaJuridica> pessoasJuridica() {
		TypedQuery<PessoaJuridica> query = manager.createQuery("from PessoaJuridica where codigo in (select fornecedor from Fornecedor)", PessoaJuridica.class);
		return query.getResultList();
	}
	
	public List<Fornecedor> todos() {
		TypedQuery<Fornecedor> query = manager.createQuery("from Fornecedor", Fornecedor.class);
		return query.getResultList();
	}
	
	public List<Fornecedor> nomesFornecedor() {
		TypedQuery<Fornecedor> query = manager.createQuery("Select new Fornecedor(f.codigo, f.fornecedor) from Fornecedor f", Fornecedor.class);
		return query.getResultList();
	}
	
	public void adicionar(Fornecedor fornecedor) {
		this.manager.persist(fornecedor);
	}
	
	public void excluir(Fornecedor fornecedor) {
		this.manager.remove(fornecedor);
	}
	
	public void editar(Fornecedor fornecedor) {
		this.manager.merge(fornecedor);
	}
	
	//
	public void adicionarPj(PessoaJuridica fornecedor) {
		this.manager.persist(fornecedor);
	}
	
	public void excluirPj(PessoaJuridica fornecedor) {
		this.manager.remove(fornecedor);
	}
	
	public void editarPj(PessoaJuridica fornecedor) {
		this.manager.merge(fornecedor);
	}
}
