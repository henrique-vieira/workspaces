package br.com.sistemaxm.repository;

import java.util.List;
import br.com.sistemaxm.entidades.Marca;

public class ListarMarcas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcaRepository marcaRepo = new MarcaRepository();
			
		List<Marca> marcas = marcaRepo.todos();
		
		for (Marca marca : marcas) {
			System.out.println(marca.getDescricao());
		}
	}
	

}
