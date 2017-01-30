package br.com.sistemaxm.business;

import br.com.sistemaxm.entidades.ItemPeca;
import br.com.sistemaxm.repository.ItemPecaRepository;

public class CadastroItemPeca {

	ItemPecaRepository itemPecaRepo;
	
	public CadastroItemPeca(ItemPecaRepository itemPecaRepo) {
		// TODO Auto-generated constructor stub
		this.itemPecaRepo = itemPecaRepo;
	}

	public CadastroItemPeca() {
		
	}
	
	public void salvar(ItemPeca itemPeca) {
		this.itemPecaRepo.adicionar(itemPeca);
	}
	
	public void editar(ItemPeca itemPeca) {
		this.itemPecaRepo.editar(itemPeca);
	}
	
	public void excluir(ItemPeca itemPeca) {
		this.itemPecaRepo.excluir(itemPeca);
	}
}
