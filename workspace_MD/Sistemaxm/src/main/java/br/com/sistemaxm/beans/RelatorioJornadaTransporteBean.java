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
import br.com.sistemaxm.entidades.Funcionario;
import br.com.sistemaxm.entidades.ItemPeca;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.entidades.Proprietario;
import br.com.sistemaxm.entidades.Servico;
import br.com.sistemaxm.report.ExecutorRelatorio;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.FuncionarioRepository;
import br.com.sistemaxm.repository.ItemPecaRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ProprietarioRepository;
import br.com.sistemaxm.repository.ServicoRepository;

@ManagedBean
@RequestScoped
public class RelatorioJornadaTransporteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date pDataInicioJornada;
	private Date pDataFimJornada;
	private List<Carro> carros;
	private Carro carro;
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	private Cliente cliente;
	private List<Cliente> clientes;
	private EntityManager manager;
	private HttpServletResponse response;
	private FacesContext context;

	public RelatorioJornadaTransporteBean() {
	
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

	public List<Cliente> autoCompleteCliente(String busca) {
		List<Cliente> auxLista = new ArrayList<Cliente>();
		EntityManager manager = JpaUtil.getEntityManager();

		try {
			ClienteRepository clienteRepo = new ClienteRepository(manager);
			this.clientes = clienteRepo.nomesCliente();
			for (Cliente c : clientes) {
				if (c.getCliente().getNome().toUpperCase()
						.startsWith(busca.toUpperCase())) {
					auxLista.add(c);
				}
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			manager.close();
		}
		return auxLista;

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
		parametros.put("data_inicio", this.pDataInicioJornada);
		parametros.put("data_fim", this.pDataFimJornada);
		
		if (this.carro != null) {
			parametros.put("pCarro", "AND c.placa = '" + this.carro.getPlaca() + "'");
		}
				
		if (this.funcionario != null) {
			parametros.put("pFuncionario", "AND p.nome = '" + this.funcionario.getPessoa().getNome()  + "'");
		}
		
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse)context.getExternalContext().getResponse();
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/RelatorioJornadaTransporte.jasper", 
				this.response, parametros, "Relatorio Jornada Transporte.pdf");
		this.manager = JpaUtil.getEntityManager();
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		context.responseComplete();	
	}


	public Date getpDataInicioJornada() {
		return pDataInicioJornada;
	}

	public void setpDataInicioJornada(Date pDataInicioJornada) {
		this.pDataInicioJornada = pDataInicioJornada;
	}

	public Date getpDataFimJornada() {
		return pDataFimJornada;
	}

	public void setpDataFimJornada(Date pDataFimJornada) {
		this.pDataFimJornada = pDataFimJornada;
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
