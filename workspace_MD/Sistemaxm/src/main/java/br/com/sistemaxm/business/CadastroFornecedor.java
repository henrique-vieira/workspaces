package br.com.sistemaxm.business;

import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.entidades.PessoaJuridica;
import br.com.sistemaxm.repository.FornecedorRepository;

public class CadastroFornecedor {
	
	private FornecedorRepository fornecedorRepo;
	
	public CadastroFornecedor(FornecedorRepository fornecedorRepo) {
		this.fornecedorRepo = fornecedorRepo;
	}
	
	public CadastroFornecedor() {
		
	}
	
	public void salvar(Fornecedor fornecedor) {
		this.fornecedorRepo.adicionar(fornecedor);
	}

	public void editar(Fornecedor fornecedor) {
		this.fornecedorRepo.editar(fornecedor);
	}
	
	public void excluir(Fornecedor fornecedor) {
		this.fornecedorRepo.excluir(fornecedor);
	}
	//
	public void salvarPj(PessoaJuridica fornecedor) {
		this.fornecedorRepo.adicionarPj(fornecedor);
	}

	public void editarPj(PessoaJuridica fornecedor) {
		this.fornecedorRepo.editarPj(fornecedor);
	}
	
	public void excluirPj(PessoaJuridica fornecedor) {
		this.fornecedorRepo.excluirPj(fornecedor);
	}
}
