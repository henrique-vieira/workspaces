package br.com.sistemaxm.business;

import java.io.Serializable;

import br.com.sistemaxm.entidades.Despesa;
import br.com.sistemaxm.repository.DespesaRepository;

public class CadastroDespesa implements Serializable {

	private static final long serialVersionUID = 1L;

	DespesaRepository despesaRepo;
	
	public CadastroDespesa(DespesaRepository despesaRepo) {
		this.despesaRepo = despesaRepo;
	}
	
	public void salvar(Despesa despesa) {
		this.despesaRepo.salvar(despesa);
	}
	
	public void excluir(Despesa despesa) {
		this.despesaRepo.excluir(despesa);
	}
	
	public void editar(Despesa despesa) {
		this.despesaRepo.editar(despesa);
	}

}
