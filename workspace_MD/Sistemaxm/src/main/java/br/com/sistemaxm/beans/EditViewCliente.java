package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import br.com.sistemaxm.business.CadastroClientes;
import br.com.sistemaxm.entidades.PessoaFisica;
import br.com.sistemaxm.entidades.PessoaJuridica;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class EditViewCliente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<PessoaFisica> clientesPf;
	private List<PessoaJuridica> clientesPj;
	private PessoaFisica pessoaFisica;
	private PessoaJuridica pessoaJuridica;
	ClienteRepository clienteRepo;
	
	public void init() {
		this.clientesPf = clienteRepo.pessoasFisica();
		this.clientesPj = clienteRepo.pessoasJuridica();
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
	
	public void onRowEditPf(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.pessoaFisica = (PessoaFisica) event.getObject();
		
		try {
			trx.begin();
			CadastroClientes cadastro = new CadastroClientes(new ClienteRepository(manager));
			this.pessoaFisica.setCodigo(((PessoaFisica)manager.find(PessoaFisica.class, pessoaFisica.getCodigo())).getCodigo());
			cadastro.salvarClientePf(pessoaFisica);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro editado com sucesso!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não editado!", null));
		}
		
		finally {
			manager.close();
		}
	}
	
	public void onRowEditPj(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.pessoaJuridica = (PessoaJuridica) event.getObject();
		
		try {
			trx.begin();
			CadastroClientes cadastro = new CadastroClientes(new ClienteRepository(manager));
			this.pessoaJuridica.setCodigo(((PessoaJuridica)manager.find(PessoaJuridica.class, pessoaJuridica.getCodigo())).getCodigo());
			cadastro.salvarClientePj(pessoaJuridica);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro editado com sucesso!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não editado!", null));
		}
		
		finally {
			manager.close();
		}
	}
	
	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		
		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro alterado!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

}
