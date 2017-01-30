package br.com.sistemaxm.business;

import java.io.Serializable;

import br.com.sistemaxm.entidades.Contrato;
import br.com.sistemaxm.repository.ContratoRepository;

public class CadastroContrato implements Serializable {

	private static final long serialVersionUID = 1L;
	
	ContratoRepository contratoRepo;
	
	public CadastroContrato(ContratoRepository contratoRepo) {
		this.contratoRepo = contratoRepo;
	}
	
	public void salvar(Contrato contrato) {
		this.contratoRepo.salvar(contrato);
	}
	
	public void editar(Contrato contrato) {
		this.contratoRepo.editar(contrato);
	}
	
	public void excluir(Contrato contrato) {
		this.contratoRepo.excluir(contrato);
	}

}
