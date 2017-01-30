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
import br.com.sistemaxm.entidades.ItemPeca;
import br.com.sistemaxm.entidades.Proprietario;
import br.com.sistemaxm.enums.TipoDespesa;
import br.com.sistemaxm.report.ExecutorRelatorio;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ItemPecaRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ProprietarioRepository;

@ManagedBean
@RequestScoped
public class RelatorioDespesasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date pDataInicio;
	private Date pDataFim;
	private List<Carro> carros;
	private Carro carro;
	private List<TipoDespesa> tipos;
	private TipoDespesa tipoDespesa;
	private EntityManager manager;
	private HttpServletResponse response;
	private FacesContext context;

	public RelatorioDespesasBean() {
		
	}
	
	public List<Carro> autoCompleteCarro(String busca) {
		List<Carro> auxLista = new ArrayList<Carro>();
		EntityManager manager = JpaUtil.getEntityManager();
		try {
			CarroRepository carroRepo = new CarroRepository(
					manager);
			this.carros = carroRepo.listaCarros();
			for (Carro c : carros) {
				if (c.getModelo().toLowerCase().startsWith(busca.toLowerCase()) 
						|| c.getPlaca().toLowerCase().startsWith(busca.toLowerCase())) {
				auxLista.add(c);
				}
			}
			return auxLista;
		} finally {
			manager.close();
		}
	}
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.pDataInicio);
		parametros.put("data_fim", this.pDataFim);
		
		if (this.carro != null) {
			parametros.put("pCarro", "AND xm.carro.placa = '" + this.carro.getPlaca() + "'");
		}
		
		
		
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse)context.getExternalContext().getResponse();
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/RelatorioDeDespesa.jasper", 
				this.response, parametros, "Relatorio de Despesas.pdf");
		this.manager = JpaUtil.getEntityManager();
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		context.responseComplete();	
	}

	public Date getpDataInicio() {
		return pDataInicio;
	}

	public void setpDataInicio(Date pDataInicio) {
		this.pDataInicio = pDataInicio;
	}

	public Date getpDataFim() {
		return pDataFim;
	}

	public void setpDataFim(Date pDataFim) {
		this.pDataFim = pDataFim;
	}

	public List<TipoDespesa> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoDespesa> tipos) {
		this.tipos = tipos;
	}

	public TipoDespesa getTipoDespesa() {
		return tipoDespesa;
	}

	public void setTipoDespesa(TipoDespesa tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}

	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	
}
