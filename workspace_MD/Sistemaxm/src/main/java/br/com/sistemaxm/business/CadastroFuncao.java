package br.com.sistemaxm.business;

import br.com.sistemaxm.entidades.Funcao;
import br.com.sistemaxm.repository.FuncaoRepository;

public class CadastroFuncao {

	FuncaoRepository funcaoRepo;
	
	public CadastroFuncao(FuncaoRepository funcaoRepo) {
		this.funcaoRepo = funcaoRepo;
	}
	
	public CadastroFuncao() {
		
	}
	
	public void salvar(Funcao funcao) {
		this.funcaoRepo.salvar(funcao);
	}
	
	public void editar(Funcao funcao) {
		this.funcaoRepo.editar(funcao);
	}
	
	public void excluir(Funcao funcao) {
		this.funcaoRepo.excluir(funcao);
	}

}
