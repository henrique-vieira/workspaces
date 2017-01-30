package com.algaworks.comercial.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import com.algaworks.comercial.model.Orcamento;
import com.algaworks.comercial.repository.OrcamentosRepository;
import com.algaworks.comercial.util.Transacional;

public class GestaoOrcamentos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private OrcamentosRepository orcamentos;
	
	@Transacional
	public void salvar(Orcamento orcamento) {
		orcamentos.guardar(orcamento);
	}
	
	@Transacional
	public void excluir(Orcamento orcamento){
		this.orcamentos.remover(orcamento);
	}
	
	@Transacional
	public void listarTodos(Orcamento orcamento){
		this.orcamentos.todos();
	}
	
	
	
}