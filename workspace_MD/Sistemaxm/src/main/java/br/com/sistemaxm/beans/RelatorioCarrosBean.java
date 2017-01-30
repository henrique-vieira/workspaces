package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.entidades.Proprietario;
import br.com.sistemaxm.report.ExecutorRelatorio;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.FornecedorRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ProprietarioRepository;

@ManagedBean
@RequestScoped
public class RelatorioCarrosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Proprietario proprietario;
	private Carro carro;
	private List<Proprietario> listaProprietarios;
	private List<Carro> carros;
	private EntityManager manager;
	private HttpServletResponse response;
	private FacesContext context;

	public List<Proprietario> getListaProprietarios() {
		return listaProprietarios;
	}

	public void setListaProprietarios(List<Proprietario> listaProprietarios) {
		this.listaProprietarios = listaProprietarios;
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

	public RelatorioCarrosBean() {
		this.carro = new Carro(new Long(0), new String(), new Long(0));
		this.proprietario = new Proprietario(new Long(0), new Pessoa());
	}

	public List<Carro> autoCompleteCarro(String busca) {
		List<Carro> auxLista = new ArrayList<Carro>();
		EntityManager manager = JpaUtil.getEntityManager();

		try {
			CarroRepository carroRepo = new CarroRepository(manager);
			this.carros = carroRepo.listaCarros();
			for (Carro c : carros) {

				if (c.getModelo().toLowerCase().startsWith(busca.toLowerCase())) {
					auxLista.add(c);
				}
			}
			return auxLista;
		} finally {
			manager.close();
		}
	}

	public List<Proprietario> autoCompleteProprietario(String busca) {
		List<Proprietario> auxLista = new ArrayList<Proprietario>();
		EntityManager manager = JpaUtil.getEntityManager();
		try {
			ProprietarioRepository proprietarioRepo = new ProprietarioRepository(
					manager);
			this.listaProprietarios = proprietarioRepo.nomesProprietario();

			for (Proprietario p : listaProprietarios) {
				if (p.getProprietario().getNome().toLowerCase()
						.startsWith(busca.toLowerCase())) {
					auxLista.add(p);
				}
			}
			return auxLista;
		} finally {
			manager.close();
		}
	}

	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();

		if (this.proprietario != null) {
			parametros.put("proprietario", "AND xm.pessoa.nome = '"	+ this.proprietario.getProprietario().getNome() + "'");
		}

		if (this.carro != null) {
			parametros.put("placa", "AND xm.carro.placa = '" + this.carro.getPlaca() + "'");
		}
		
		if (this.carro != null) {
			parametros.put("modelo", "AND xm.carro.modelo = '" + this.carro.getModelo() + "'");
		}
		
		
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) context.getExternalContext()
				.getResponse();
		ExecutorRelatorio executor = new ExecutorRelatorio(
				"/relatorios/RelatorioVeiculo.jasper", this.response,
				parametros, "Relatorio Carros.pdf");
		this.manager = JpaUtil.getEntityManager();
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		context.responseComplete();
	}

	public Proprietario getProprietario() {
		return proprietario;
	}

	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}

}
