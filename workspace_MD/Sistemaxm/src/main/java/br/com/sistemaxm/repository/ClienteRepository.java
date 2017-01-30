package br.com.sistemaxm.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.entidades.PessoaFisica;
import br.com.sistemaxm.entidades.PessoaJuridica;

public class ClienteRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	EntityManager manager;

	public ClienteRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public ClienteRepository() {
		
	}
	
	public Cliente porid(Long id) {
		return this.manager.find(Cliente.class, id);
	}
	
	public List<Cliente> todos() {
		TypedQuery<Cliente> query = manager.createQuery("from Cliente",Cliente.class);
		return query.getResultList();
	}
	
	public List<Cliente> nomesCliente() {
		TypedQuery<Cliente> query = manager.createQuery("Select new Cliente(c.codigo, c.cliente) from Cliente c ", Cliente.class);
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
		
	public void salvar(Cliente cliente) {
		this.manager.persist(cliente);
	}
	
	public void editar(Cliente cliente) {
		this.manager.merge(cliente);
	}
	
	public void excluir(Cliente cliente) {
		this.manager.remove(cliente);
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
