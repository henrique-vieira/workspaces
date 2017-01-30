package br.com.sistemaxm.business;

import br.com.sistemaxm.entidades.Servico;
import br.com.sistemaxm.repository.ServicoRepository;

public class CadastroServico {

	private ServicoRepository servicoRepo;
	
	public CadastroServico(ServicoRepository servicoRepo) {
		this.servicoRepo = servicoRepo;
	}
	
	public CadastroServico() {
		
	}
	
	public void salvar(Servico servico) {
		this.servicoRepo.adicionar(servico);
	}
	
	public void editar(Servico servico){
		this.servicoRepo.editar(servico);
	}
	
	public void excluir(Servico servico) {
		this.servicoRepo.excluir(servico);;
	}

	public ServicoRepository getServicoRepo() {
		return servicoRepo;
	}

	public void setServicoRepo(ServicoRepository servicoRepo) {
		this.servicoRepo = servicoRepo;
	}
	
	
}
