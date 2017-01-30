package br.com.sistemaxm.business;

import java.io.Serializable;

import br.com.sistemaxm.entidades.Despesa;
import br.com.sistemaxm.entidades.Receita;
import br.com.sistemaxm.repository.DespesaRepository;
import br.com.sistemaxm.repository.ReceitaRepository;

public class CadastroReceita implements Serializable {

	private static final long serialVersionUID = 1L;

	ReceitaRepository receitaRepo;
	
	public CadastroReceita(ReceitaRepository receitaRepo) {
		this.receitaRepo = receitaRepo;
	}
	
	public void salvar(Receita receita) {
		this.receitaRepo.salvar(receita);
	}
	
	public void excluir(Receita receita) {
		this.receitaRepo.excluir(receita);
	}
	
	public void editar(Receita receita) {
		this.receitaRepo.editar(receita);
	}

}
