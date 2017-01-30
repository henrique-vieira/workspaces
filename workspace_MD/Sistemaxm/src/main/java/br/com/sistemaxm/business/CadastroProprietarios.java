package br.com.sistemaxm.business;

import java.io.Serializable;

import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.PessoaFisica;
import br.com.sistemaxm.entidades.PessoaJuridica;
import br.com.sistemaxm.entidades.Proprietario;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.ProprietarioRepository;

public class CadastroProprietarios implements Serializable {
	
	ProprietarioRepository proprietarioRepo;
	
	public CadastroProprietarios(ProprietarioRepository proprietarioRepo) {
		this.proprietarioRepo = proprietarioRepo;
	}
	
	public CadastroProprietarios() {
		
	}
	
	public void salvarProprietarioPf(PessoaFisica pessoaFisica) {
		this.proprietarioRepo.editarClientePf(pessoaFisica);
	}
	
	public void salvarProprietarioPj(PessoaJuridica pessoaJuridica) {
		this.proprietarioRepo.editarClientePj(pessoaJuridica);
	}
	
	public void deletarClientePf(PessoaFisica pessoaFisica) {
		this.proprietarioRepo.excluirClientePf(pessoaFisica);
	}
	
	public void deletarClientePj(PessoaJuridica pessoaJuridica) {
		this.proprietarioRepo.excluirClientePj(pessoaJuridica);
	}
	
	public void salvar(Proprietario proprietario) {
		this.proprietarioRepo.salvar(proprietario);
	}
	
	public void editar(Proprietario proprietario) {
		this.proprietarioRepo.editar(proprietario);
	}
	
	public void excluir(Proprietario proprietario) {
		this.proprietarioRepo.excluir(proprietario);
	}
}

