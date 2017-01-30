package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.RowEditEvent;

import br.com.sistemaxm.business.CadastroContrato;
import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.Contrato;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.entidades.PessoaJuridica;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.ContratoRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class CadastroContratoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Contrato contrato;
	private Carro carro;
	private Cliente cliente;
	private List<Carro> carros;
	private List<Cliente> clientes;
	private List<Contrato> contratos;
	
	public CadastroContratoBean() {
		this.contrato = new Contrato();
		
	}
	
	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
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

	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}
	
	public void listaContratos() {
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			ContratoRepository contratoRepo = new ContratoRepository(manager);
			this.contratos = contratoRepo.todos();
		}
		catch(Exception e) {
			e.getMessage();
		}
		finally {
			manager.close();
		}
	}
	
	public List<Carro> autoCompleteCarro(String busca) {
		List<Carro> auxLista = new ArrayList<Carro>();
		EntityManager manager = JpaUtil.getEntityManager();

		try {
			CarroRepository carroRepo = new CarroRepository(manager);
			this.carros = carroRepo.listaCarros();
			for (Carro c : carros) {
				if (c.getPlaca().toLowerCase().startsWith(busca.toLowerCase())
						|| c.getModelo().toLowerCase()
								.startsWith(busca.toLowerCase())) {
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
				if (c.getCliente().getNome().toLowerCase().startsWith(busca.toLowerCase())) {
					auxLista.add(c);
				}
			}
			return auxLista;
		} finally {
			manager.close();
		}
	}

	
	/*public void atualizaValorKm(AjaxBehaviorEvent event) {
		this.contrato.setValorKm(this.contrato.getValorFixo()/this.contrato.getQtdeKmContrato());
		this.contrato.getValorKm();
	
	}*/
	
	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			this.contrato.setCarro(this.carro);
			this.contrato.setCliente(this.cliente);
			CadastroContrato cadastro = new CadastroContrato(new ContratoRepository(manager));
			cadastro.salvar(this.contrato);
			trx.commit();
			this.contrato = new Contrato();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Contrato cadastrado com sucesso!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cadastrar contrato, verifique se já existe registro para este contrato !", null));
		}
		finally {
			manager.close();
		}
	}
	
	public void excluir() {
		EntityManager manager = JpaUtil.getEntityManager();
		FacesContext context = FacesContext.getCurrentInstance();
		EntityTransaction trx = manager.getTransaction();
		
		try {
			trx.begin();
			CadastroContrato cadastro = new CadastroContrato(new ContratoRepository(manager));
			this.contrato = manager.find(Contrato.class, this.contrato.getCodigo());
			cadastro.excluir(contrato);
			trx.commit();
			this.contrato = new Contrato();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro excluído com sucesso!",null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao excluir registro.",null));
		}
		finally {
			manager.close();
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		FacesContext context = FacesContext.getCurrentInstance();
		EntityTransaction trx = manager.getTransaction();
		this.contrato = (Contrato)event.getObject();
		
		try {
			trx.begin();
			CadastroContrato cadastro = new CadastroContrato(new ContratoRepository(manager));
			this.contrato.setCodigo(((Contrato)manager.find(Contrato.class, this.contrato.getCodigo())).getCodigo());			
			cadastro.editar(contrato);
			this.contrato = new Contrato();
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro editado com sucesso!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não editado.", null));
		}
		finally {
			manager.close();
		}
	}

}
