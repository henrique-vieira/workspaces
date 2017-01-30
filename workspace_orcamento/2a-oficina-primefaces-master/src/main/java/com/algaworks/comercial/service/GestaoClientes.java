package com.algaworks.comercial.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.algaworks.comercial.model.Cliente;
import com.algaworks.comercial.repository.ClientesRepository;
import com.algaworks.comercial.util.Transacional;

public class GestaoClientes implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private ClientesRepository clientes;
	
	@Inject
	private EntityManager manager;
	
	@Transacional
	public void salvar(Cliente cliente){
		clientes.guardar(cliente);
	}
	
	@Transacional
	public void excluir(Cliente cliente){
		clientes.remover(cliente);
	}
	
	
	public void todos(Cliente cliente){
		clientes.buscarTodos();
	}
	
	public void porId(Cliente cliente){
		clientes.porid(cliente.getId());
	}
	
	public void nomesClientes(Cliente cliente){
		clientes.nomesCliente();
	}
	
}
