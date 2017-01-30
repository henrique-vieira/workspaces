package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.model.DualListModel;

import br.com.sistemaxm.business.CadastroLocacao;
import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.ContratoLocacao;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.ContratoLocacaoRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class ContratoLocacaoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private DualListModel<Carro> carros;
	private List<Cliente> clientes;
	private ContratoLocacao contratoLocacao;
	private Cliente cliente;
	
	public ContratoLocacaoBean() {
		this.cliente = new Cliente(new Long(0), new Pessoa());
		this.contratoLocacao = new ContratoLocacao();		
	}

	
	public List<Cliente> autocompleteCliente(String busca) {
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
	
	public Long recuperaNumeroLocacao() {
		EntityManager manager = JpaUtil.getEntityManager();
		Long maxLoc;
		try {
			ContratoLocacaoRepository contratoLocRepo = new ContratoLocacaoRepository(manager);
			maxLoc = contratoLocRepo.numLocacao();
			
		}
		finally {
			manager.close();
		}
		return maxLoc+1;
		
	}

	@PostConstruct
	public void init() {

		EntityManager manager = JpaUtil.getEntityManager();
		try {
			CarroRepository carroRepo;
			carroRepo = new CarroRepository(manager);
			List<Carro> carrosSource = new ArrayList<Carro>();
			List<Carro> carrosTarget = new ArrayList<Carro>();
			carrosSource = carroRepo.veiculosCombo();
			this.carros = new DualListModel<Carro>(carrosSource, carrosTarget);

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			manager.close();
		}

	}

	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Long nextLoc = recuperaNumeroLocacao();
		
		try {
			trx.begin();
			
			CadastroLocacao cadastro = new CadastroLocacao(
					new ContratoLocacaoRepository(manager));			
			
			Date dataInicioLocacao = this.contratoLocacao.getDataInicioLocacao();
			Date dataFimLocacao = this.contratoLocacao.getDataFimLocacao();
			for (Carro carro : carros.getTarget()) {
											
				this.contratoLocacao.setNumeroLocacao(nextLoc);
				this.contratoLocacao.setCliente(cliente);
				this.contratoLocacao.setKmSaida(carro.getKm());
				this.contratoLocacao.setCarro(carro);
				cadastro.salvar(this.contratoLocacao);
				this.contratoLocacao = new ContratoLocacao();
				this.contratoLocacao.setDataInicioLocacao(dataInicioLocacao);
				this.contratoLocacao.setDataFimLocacao(dataFimLocacao);
								
			}
						
			this.cliente = new Cliente();
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Locação cadastrada com sucesso!", null));

			trx.commit();
			
			
		}

		catch (Exception e) {
			trx.rollback();
			facesContext
					.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Locação não cadastrada!", null));
		}

		finally {
			manager.close();
		}
	}

	public DualListModel<Carro> getCarros() {
		return carros;
	}

	public void setCarros(DualListModel<Carro> carros) {
		this.carros = carros;
	}

	
	public ContratoLocacao getContratoLocacao() {
		return contratoLocacao;
	}

	
	public List<Cliente> getClientes() {
		return clientes;
	}


	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}


	public void setContratoLocacao(ContratoLocacao contratoLocacao) {
		this.contratoLocacao = contratoLocacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
