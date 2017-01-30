package br.com.sistemaxm.business;

import br.com.sistemaxm.entidades.ContratoLocacao;
import br.com.sistemaxm.repository.ContratoLocacaoRepository;

public class CadastroLocacao {

	ContratoLocacaoRepository contratoLocacaoRepo;
	
	public CadastroLocacao(ContratoLocacaoRepository contratoLocacaoRepo) {
		this.contratoLocacaoRepo = contratoLocacaoRepo;
	}
	public CadastroLocacao() {
		
	}
	
	public void salvar(ContratoLocacao contratoLocacao) {
		this.contratoLocacaoRepo.salvar(contratoLocacao);
	}
	
	public void editar(ContratoLocacao contratoLocacao) {
		this.contratoLocacaoRepo.editar(contratoLocacao);
	}
	
	public void excluir(ContratoLocacao contratoLocacao) {
		this.contratoLocacaoRepo.excluir(contratoLocacao);;
	}
}
