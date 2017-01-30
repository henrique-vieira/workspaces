package br.com.sistemaxm.business;

import br.com.sistemaxm.entidades.Manutencao;
import br.com.sistemaxm.repository.ManutencaoRepository;

public class CadastroManutencao {

	ManutencaoRepository manutencaoRepo;
	
	public CadastroManutencao(ManutencaoRepository manutencaoRepo) {
		this.manutencaoRepo = manutencaoRepo;
	}
	
	public CadastroManutencao() {
		
	}

	public void salvar(Manutencao manutencao) {	
		this.manutencaoRepo.adicionar(manutencao);
	}
	
	public void editar(Manutencao manutencao) {
		this.manutencaoRepo.editar(manutencao);
	}
	
	public void excluir(Manutencao manutencao) {
		this.manutencaoRepo.excluir(manutencao);
	}
}
