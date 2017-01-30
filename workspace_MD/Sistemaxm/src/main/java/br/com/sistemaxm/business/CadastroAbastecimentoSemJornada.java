package br.com.sistemaxm.business;

import br.com.sistemaxm.entidades.AbastecimentoSemJornada;
import br.com.sistemaxm.repository.AbastecimentoSemJornadaRepository;

public class CadastroAbastecimentoSemJornada {

	AbastecimentoSemJornadaRepository abastecimentoSemJornadaRepo;
	
	public CadastroAbastecimentoSemJornada(AbastecimentoSemJornadaRepository abastecimentoSemJornadaRepo) {
		this.abastecimentoSemJornadaRepo = abastecimentoSemJornadaRepo;
	}
	
	public void salvar(AbastecimentoSemJornada abastecimentoSemJornada) {
		this.abastecimentoSemJornadaRepo.salvar(abastecimentoSemJornada);
	}
	
	public void excluir(AbastecimentoSemJornada abastecimentoSemJornada) {
		this.abastecimentoSemJornadaRepo.excluir(abastecimentoSemJornada);
	}
	
	public void editar(AbastecimentoSemJornada abastecimentoSemJornada) {
		this.abastecimentoSemJornadaRepo.editar(abastecimentoSemJornada);
	}

}
