package br.com.sistemaxm.business;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.sistemaxm.entidades.Marca;
import br.com.sistemaxm.repository.MarcaRepository;

public class CadastroMarcas {
	
	private static final long serialVersionUID = 1L;
	
	private MarcaRepository marcaRepo;
	
	
	public CadastroMarcas(MarcaRepository marcaRepo) {
		this.marcaRepo = marcaRepo;
	}
	
	public CadastroMarcas() {
		
	}

	public void salvar(Marca marca) {
			
				this.marcaRepo.adicionar(marca);
			
		}
	
	public void excluir(Marca marca) {
		this.marcaRepo.remover(marca);
	}
	
	public void editar(Marca marca) {
		this.marcaRepo.atualizar(marca);
	}

}		
	
