package br.com.sistemaxm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.PessoaFisica;
import br.com.sistemaxm.entidades.PessoaJuridica;
import br.com.sistemaxm.entidades.Proprietario;

public class ProprietarioRepository {

	EntityManager manager;
	
	public ProprietarioRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public ProprietarioRepository() {
		
	}
	
	public Proprietario porId(Long id) {
		return this.manager.find(Proprietario.class, id);
	}
	
	public List<Proprietario> todos() {
		TypedQuery<Proprietario> query = manager.createQuery("from Proprietario",Proprietario.class);
		return query.getResultList();
	}
	
	public List<Proprietario> nomesProprietario() {
		TypedQuery<Proprietario> query = manager.createQuery("Select new Proprietario(p.codigo, p.proprietario) from Proprietario p", Proprietario.class);
		return query.getResultList();		
	}
	
	public List<PessoaFisica> pessoasFisica() {
		TypedQuery<PessoaFisica> query = manager.createQuery("from PessoaFisica pf where pf.codigo in (select cl.cliente from Cliente cl)", PessoaFisica.class);
		return query.getResultList();
	}
	
	public List<PessoaJuridica> pessoasJuridica() {
		TypedQuery<PessoaJuridica> query = manager.createQuery("from PessoaJuridica pj where pj.codigo in (select cl.cliente from Cliente cl)", PessoaJuridica.class);
		return query.getResultList();
	}
	
	public void salvar(Proprietario proprietario) {
		this.manager.persist(proprietario);
	}
	
	public void editar(Proprietario proprietario) {
		this.manager.merge(proprietario);
	}
	
	public void excluir(Proprietario proprietario) {
		this.manager.remove(proprietario);
	}
	
	public void editarClientePf(PessoaFisica pessoaFisica) {
		this.manager.merge(pessoaFisica);
	}
	
	public void editarClientePj(PessoaJuridica pessoaJuridica) {
		this.manager.merge(pessoaJuridica);
	}
	
	public void excluirClientePf(PessoaFisica pessoaFisica) {
		this.manager.remove(pessoaFisica);
	}
	
	public void excluirClientePj(PessoaJuridica pessoaJuridica) {
		this.manager.remove(pessoaJuridica);
	}
}
