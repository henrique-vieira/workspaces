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
import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.entidades.Funcionario;
import br.com.sistemaxm.report.ExecutorRelatorio;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.FornecedorRepository;
import br.com.sistemaxm.repository.FuncionarioRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@RequestScoped
public class RelatorioCentroCustosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date pDataInicio;
	private Date pDataFim;
	private Carro carro;		
	private List<Carro> carros;
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	private EntityManager manager;
	private HttpServletResponse response;
	private FacesContext context;
	
	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}
	

	public RelatorioCentroCustosBean() {
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
	public List<Funcionario> autoCompleteFuncionario(String busca) {
		List<Funcionario> auxLista = new ArrayList<Funcionario>();
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			FuncionarioRepository funcionarioRepo = new FuncionarioRepository(manager);
			this.funcionarios = funcionarioRepo.nomesFuncionarios();
			
			for (Funcionario f : this.funcionarios) {
				if (f.getPessoa().getNome().toUpperCase().startsWith(busca.toUpperCase())) {
					auxLista.add(f);
				}
			}
		}
		catch(Exception e) {
			e.getMessage();
		}
		
		finally {
			manager.close();
		}
		return auxLista;
	}
	
	public void emitir() {
		Map<String, Object> parametros = new HashMap<>(); 
		parametros.put("data_inicio", this.pDataInicio);
		parametros.put("data_fim", this.pDataFim);	

				
		if (this.carro != null) {
			parametros.put("pCarro", " AND c.placa = '"+this.carro.getPlaca()+"'");
		}
		
				
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse)context.getExternalContext().getResponse();
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/RelatorioCentroDeCustos.jasper", 
				this.response, parametros, "Centro de Custos.pdf");
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

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

		
}
