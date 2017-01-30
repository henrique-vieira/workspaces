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
import br.com.sistemaxm.report.ExecutorRelatorio;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.FornecedorRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@RequestScoped
public class RelatorioCotacaoPecasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date pDataCotacaoInicio;
	private Date pDataCotacaoFim;
	private Carro carro;		
	private List<Carro> carros;
	private EntityManager manager;
	private HttpServletResponse response;
	private FacesContext context;
	
	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}
	

	public RelatorioCotacaoPecasBean() {
		this.carro = new Carro(new Long(0), new String(), new String(), new Long(0));
	}
	
	public List<Carro> autoCompleteCarro(String busca) {
		List<Carro> auxLista = new ArrayList<Carro>();
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			CarroRepository carroRepo = new CarroRepository(manager);
			this.carros = carroRepo.listaCarros();
			for (Carro c : carros) {
				
				if (c.getPlaca().toLowerCase().startsWith(busca.toLowerCase())) {
					auxLista.add(c);
				}
			}
			return auxLista;
		}
		finally {
			manager.close();
		}
	}
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>(); 
		parametros.put("data_inicio", this.pDataCotacaoInicio);
		parametros.put("data_fim", this.pDataCotacaoFim);			
		
		if (this.carro != null) {
			parametros.put("P_SQL", " AND xm.carro.placa = '"+this.carro.getPlaca()+"'");
		}
		
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse)context.getExternalContext().getResponse();
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/RelatorioDeCotacao.jasper", 
				this.response, parametros, "Relatorio Cotacao.pdf");
		this.manager = JpaUtil.getEntityManager();
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		context.responseComplete();	
	}

	public Date getpDataCotacaoInicio() {
		return pDataCotacaoInicio;
	}

	public void setpDataCotacaoInicio(Date pDataCotacaoInicio) {
		this.pDataCotacaoInicio = pDataCotacaoInicio;
	}

	public Date getpDataCotacaoFim() {
		return pDataCotacaoFim;
	}

	public void setpDataCotacaoFim(Date pDataCotacaoFim) {
		this.pDataCotacaoFim = pDataCotacaoFim;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	
	
}
