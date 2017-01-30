package br.com.sistemaxm.business;

import br.com.sistemaxm.entidades.Funcionario;
import br.com.sistemaxm.repository.FuncionarioRepository;

public class CadastroFuncionario {
	
	FuncionarioRepository funcionarioRepo;

	public CadastroFuncionario(FuncionarioRepository funcionarioRepo) {
		this.funcionarioRepo = funcionarioRepo;
	}
	
	public CadastroFuncionario() {
		
	}
	
	public void salvar(Funcionario funcionario) {
		this.funcionarioRepo.salvar(funcionario);
	}

	public void editar(Funcionario funcionario) {
		this.funcionarioRepo.editar(funcionario);
	}
	
	public void excluir(Funcionario funcionario) {
		this.funcionarioRepo.excluir(funcionario);
	}

}
