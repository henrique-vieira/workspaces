package br.com.sistemaxm.business;

import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.repository.CarroRepository;

public class CadastroCarros {

	private CarroRepository carroRepo;
	
	public CadastroCarros(CarroRepository carroRepo) {
		this.carroRepo = carroRepo;
	}
	
	public CadastroCarros() {
		
	}
	
	public void editar(Carro carro) {
		this.carroRepo.editar(carro);
	}
	
	public void salvar(Carro carro) {
		this.carroRepo.adicionar(carro);
	}
	
	public void excluir(Carro carro) {
		this.carroRepo.excluir(carro);
	}
	
}
