package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.Marca;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.entidades.Proprietario;
import br.com.sistemaxm.enums.Cambio;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.MarcaRepository;
import br.com.sistemaxm.repository.ProprietarioRepository;

@ManagedBean
@ViewScoped
public class ConsultaCarroBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Carro carro;
	private Proprietario proprietario;
	private List<Carro> carros;
	private List<Marca> marcas;
	private List<Proprietario> listaProprietarios;
	
	
	public ConsultaCarroBean() {
		this.proprietario = new Proprietario(new Long(0), new Pessoa());		
	}
			
	public List<Proprietario> getListaProprietarios() {
		return listaProprietarios;
	}

	public void setListaProprietarios(List<Proprietario> listaProprietarios) {
		this.listaProprietarios = listaProprietarios;
	}

	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}
	
	public Cambio[] getCambio() {
		return Cambio.values();
	}
	
	public List<SelectItem> getTipoItemListCambio() {
		List<SelectItem> auxLista = new ArrayList<SelectItem>();
		for (Cambio cambioEnum : Cambio.values()) {
			auxLista.add(new SelectItem(cambioEnum, cambioEnum.getValue()));
		}
		return auxLista;
	}

	public void init() {
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			MarcaRepository marcaRepo = new MarcaRepository(manager);
			this.marcas = marcaRepo.todos();
		}
		finally {
			manager.close();
		}
	}
	
	public void consultar() {
		EntityManager manager = JpaUtil.getEntityManager();
		CarroRepository carroRepo = new CarroRepository(manager);
		
		this.carros = carroRepo.todos();
	}
	
	public List<Proprietario> autoCompleteProprietario(String busca) {
		List<Proprietario> auxLista = new ArrayList<Proprietario>();
		EntityManager manager = JpaUtil.getEntityManager();
		try {			
			ProprietarioRepository proprietarioRepo = new ProprietarioRepository(manager);
			this.listaProprietarios = proprietarioRepo.nomesProprietario();
			
			for (Proprietario p : listaProprietarios) {
				if (p.getProprietario().getNome().toLowerCase().startsWith(busca.toLowerCase())) {
					auxLista.add(p);
				}
			}
			return auxLista;
		}
		finally {
			manager.close();
		}
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}

	public Proprietario getProprietario() {
		return proprietario;
	}

	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}
	
	
}
