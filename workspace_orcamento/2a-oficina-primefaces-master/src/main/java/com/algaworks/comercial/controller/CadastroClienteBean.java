package com.algaworks.comercial.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.comercial.model.Cliente;
import com.algaworks.comercial.repository.ClientesRepository;
import com.algaworks.comercial.service.GestaoClientes;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GestaoClientes gestaoClientes;
	
	private Cliente cliente;
	
	private List<Cliente> clientes;
	
	private ClientesRepository clientesRepository;
	
	public CadastroClienteBean(){
		this.cliente = cliente;
		this.clientesRepository = clientesRepository;
		this.clientes = new ArrayList<Cliente>();
		
	}
	
	public void salvar() {
		gestaoClientes.salvar(cliente);
		cliente = new Cliente();//instancia um novo objeto vazio (limpa os campos após um cadastro)
		
		FacesMessage msg = new FacesMessage("Cliente salvo com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void excluir(){
		gestaoClientes.excluir(cliente);
        cliente = new Cliente();//instancia um novo objeto vazio (limpa os campos após um cadastro)
		
		FacesMessage msg = new FacesMessage("Cliente salvo com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void listar(){
		gestaoClientes.todos(cliente);
		
	}
	
	public GestaoClientes getGestaoClientes() {
		return gestaoClientes;
	}

	public void setGestaoClientes(GestaoClientes gestaoClientes) {
		this.gestaoClientes = gestaoClientes;
	}

	public ClientesRepository getClientesRepository() {
		return clientesRepository;
	}

	public void setClientesRepository(ClientesRepository clientesRepository) {
		this.clientesRepository = clientesRepository;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	
	
	
}
