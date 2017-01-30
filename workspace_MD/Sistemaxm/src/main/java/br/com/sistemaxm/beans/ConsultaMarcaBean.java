package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import br.com.sistemaxm.entidades.Marca;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.MarcaRepository;

@ManagedBean
@ViewScoped
public class ConsultaMarcaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Marca> marcas;
	private Marca marca;
	
		
	public void consultar() {
		EntityManager manager = JpaUtil.getEntityManager();
		MarcaRepository marcaRepo = new MarcaRepository(manager);
		
		this.marcas = marcaRepo.todos();
	}
	
	public List<Marca> getMarcas() {
		return marcas;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	
}
