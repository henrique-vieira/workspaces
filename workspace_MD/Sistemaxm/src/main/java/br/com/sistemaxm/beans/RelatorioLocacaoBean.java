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

import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.report.ExecutorRelatorio;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@RequestScoped
public class RelatorioLocacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date pDataCotacaoInicio;
	private Date pDataCotacaoFim;
	private Cliente cliente;
	private List<Cliente> clientes;
	private EntityManager manager;
	private HttpServletResponse response;
	private FacesContext context;

	public RelatorioLocacaoBean() {
		this.cliente = new Cliente(new Long(0), new Pessoa());
	}
	
	public List<Cliente> autoCompleteCliente(String busca) {
		List<Cliente> auxLista = new ArrayList<Cliente>();
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			ClienteRepository clienteRepo = new ClienteRepository(manager);
			this.clientes = clienteRepo.nomesCliente();
			for (Cliente c : clientes) {
				if (c.getCliente().getNome().toLowerCase().startsWith(busca.toLowerCase())) {
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
		parametros.put("Nome", this.cliente.getCliente().getNome());
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse)context.getExternalContext().getResponse();
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/RelatorioLocacoes.jasper", 
				this.response, parametros, "Relatorio Locacoes.pdf");
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
