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
import br.com.sistemaxm.report.ExecutorRelatorio;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.FornecedorRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@RequestScoped
public class RelatorioContratosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date pDataContratoInicio;
	private Date pDataContratoFim;
	private Cliente cliente;
	private Carro carro;		
	private List<Carro> carros;
	private List<Cliente> clientes;
	private EntityManager manager;
	private HttpServletResponse response;
	private FacesContext context;
	
	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}
	

	public RelatorioContratosBean() {
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
	public List<Cliente> autoCompleteCliente(String busca) {
		List<Cliente> auxLista = new ArrayList<Cliente>();
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			ClienteRepository clienteRepo = new ClienteRepository(manager);
			this.clientes = clienteRepo.nomesCliente();
			for (Cliente cl : clientes) {
				
				if (cl.getCliente().getNome().toLowerCase().startsWith(busca.toLowerCase())) {
					auxLista.add(cl);
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
		parametros.put("data_inicio", this.pDataContratoInicio);
		parametros.put("data_fim", this.pDataContratoFim);	
		//parametros.put("Nome", this.cliente.getCliente().getNome());
				
		if (this.carro != null) {
			parametros.put("pCarro", " AND xm.carro.placa = '"+this.carro.getPlaca()+"'");
		}
		
		if (this.cliente != null) {
			parametros.put("Nome", " AND xm.pessoa.nome = '"+this.cliente.getCliente().getNome()+"'");
		}
				
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse)context.getExternalContext().getResponse();
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/RelatorioDeContrato.jasper", 
				this.response, parametros, "Relatorio Cotrato.pdf");
		this.manager = JpaUtil.getEntityManager();
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		context.responseComplete();	
	}

	

	public Date getpDataContratoInicio() {
		return pDataContratoInicio;
	}

	public void setpDataContratoInicio(Date pDataContratoInicio) {
		this.pDataContratoInicio = pDataContratoInicio;
	}

	public Date getpDataContratoFim() {
		return pDataContratoFim;
	}

	public void setpDataContratoFim(Date pDataContratoFim) {
		this.pDataContratoFim = pDataContratoFim;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	
	
	
}
