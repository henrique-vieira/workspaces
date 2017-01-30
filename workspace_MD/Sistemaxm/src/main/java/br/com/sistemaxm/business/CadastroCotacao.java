package br.com.sistemaxm.business;

import javax.persistence.EntityManager;

import br.com.sistemaxm.entidades.CotacaoPecas;
import br.com.sistemaxm.repository.CotacaoRepository;
import br.com.sistemaxm.repository.JpaUtil;

public class CadastroCotacao {

	EntityManager manager = JpaUtil.getEntityManager();
	
	private CotacaoRepository cotacaoRepo;
	
	public CadastroCotacao() {
		
	}
	
	public CadastroCotacao(CotacaoRepository cotacaoRepo) {
		this.cotacaoRepo = cotacaoRepo;
	}

	public CotacaoRepository getCotacaoRepo() {
		return cotacaoRepo;
	}

	public void setCotacaoRepo(CotacaoRepository cotacaoRepo) {
		this.cotacaoRepo = cotacaoRepo;
	}
	
	public void salvar(CotacaoPecas cotacaoPecas) {
		this.cotacaoRepo.adicionar(cotacaoPecas);
	}
	
	public void editar(CotacaoPecas cotacaoPecas) {
		this.cotacaoRepo.editar(cotacaoPecas);
	}
	
	public void excluir(CotacaoPecas cotacaoPecas) {
		this.cotacaoRepo.excluir(cotacaoPecas);
	}
}
