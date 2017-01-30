package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.sistemaxm.business.CadastroClientes;
import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.entidades.PessoaFisica;
import br.com.sistemaxm.entidades.PessoaJuridica;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.ContratoLocacaoRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class ConsultaClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;
	private List<Cliente> clientes;
	private PessoaFisica pessoaFisica;
	private PessoaJuridica pessoaJuridica;
	private List<PessoaFisica> clientesPf;
	private List<PessoaJuridica> clientesPj;
	
	public ConsultaClienteBean() {
		this.cliente = new Cliente();
		this.pessoaFisica = new PessoaFisica();
		this.pessoaJuridica = new PessoaJuridica();
	}

	public void consultarClientes() {
		EntityManager manager = JpaUtil.getEntityManager();
		ClienteRepository clienteRepo = new ClienteRepository(manager);
		
		try {
			this.clientesPf = clienteRepo.pessoasFisica();
			this.clientesPj = clienteRepo.pessoasJuridica();
		}
		finally {
			manager.close();
		}
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}


	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}


	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}


	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public List<PessoaFisica> getClientesPf() {
		return clientesPf;
	}

	public void setClientesPf(List<PessoaFisica> clientesPf) {
		this.clientesPf = clientesPf;
	}

	public List<PessoaJuridica> getClientesPj() {
		return clientesPj;
	}

	public void setClientesPj(List<PessoaJuridica> clientesPj) {
		this.clientesPj = clientesPj;
	}
		
	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public void excluirPj() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroClientes cadastro = new CadastroClientes(new ClienteRepository(manager));
			this.pessoaJuridica = manager.find(PessoaJuridica.class, this.pessoaJuridica.getCodigo());
			this.cliente = manager.find(Cliente.class, this.pessoaJuridica.getCodigo());
			cadastro.excluir(cliente);
			cadastro.deletarClientePj(this.pessoaJuridica);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro excluido com sucesso!",null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não excluido!", null));
		}
		
		finally {
			manager.close();
		}
	}
	
	public void excluirPf() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroClientes cadastro = new CadastroClientes(new ClienteRepository(manager));
			this.pessoaFisica = manager.find(PessoaFisica.class, this.pessoaFisica.getCodigo());
			this.cliente = manager.find(Cliente.class, this.pessoaFisica.getCodigo());
			cadastro.excluir(cliente);
			cadastro.deletarClientePf(this.pessoaFisica);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro excluido com sucesso!",null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não excluido!", null));
		}
		
		finally {
			manager.close();
		}
	}
	
}
