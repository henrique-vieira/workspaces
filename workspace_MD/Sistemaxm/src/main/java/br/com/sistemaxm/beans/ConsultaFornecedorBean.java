package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.RowEditEvent;

import br.com.sistemaxm.business.CadastroFornecedor;
import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.entidades.PessoaJuridica;
import br.com.sistemaxm.repository.FornecedorRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class ConsultaFornecedorBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private PessoaJuridica pessoaJuridica;
	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;
	private List<PessoaJuridica> pessoasJuridica;

	public ConsultaFornecedorBean() {
		this.fornecedor = new Fornecedor();
		this.pessoaJuridica = new PessoaJuridica();
	}
	
	public void consultar() {
		EntityManager manager = JpaUtil.getEntityManager();
		FornecedorRepository fornecedorRepo = new FornecedorRepository(manager);
		
		try {
			this.pessoasJuridica = fornecedorRepo.pessoasJuridica();	
			this.fornecedores = fornecedorRepo.todos();
		}
		finally {
			manager.close();
		}
	}
	// Metodo para recuperar o fornecedor e evitar a exceção "Transact Not Active"
	public Fornecedor recuperaFornecedor(PessoaJuridica pessoaJuridica) {
		
		for (Fornecedor f : this.fornecedores) {
			if (pessoaJuridica.getCodigo().equals(f.getFornecedor().getCodigo())) {
				this.fornecedor = f;
			}
		}
		return this.fornecedor;
		
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
		
	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public List<PessoaJuridica> getPessoasJuridica() {
		return pessoasJuridica;
	}

	public void setPessoasJuridica(List<PessoaJuridica> pessoasJuridica) {
		this.pessoasJuridica = pessoasJuridica;
	}
	
	public void excluir() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroFornecedor cadastro = new CadastroFornecedor(new FornecedorRepository(manager));
			this.pessoaJuridica = manager.find(PessoaJuridica.class, this.pessoaJuridica.getCodigo());
			recuperaFornecedor(this.pessoaJuridica);
			this.fornecedor = manager.find(Fornecedor.class, this.fornecedor.getCodigo());
			cadastro.excluir(this.fornecedor);
			cadastro.excluirPj(pessoaJuridica);			
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro excluido com sucesso", null));
			this.fornecedor = new Fornecedor();
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não excluido!", null));
		}
		finally {
			manager.close();
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.pessoaJuridica = (PessoaJuridica) event.getObject();
		
		try {
			trx.begin();
			CadastroFornecedor cadastro = new CadastroFornecedor(new FornecedorRepository(manager));
			this.pessoaJuridica.setCodigo(((PessoaJuridica)manager.find(PessoaJuridica.class,this.pessoaJuridica.getCodigo())).getCodigo());
			cadastro.editarPj(pessoaJuridica);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro alterado com sucesso!", null));
			this.pessoaJuridica = new PessoaJuridica();
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não alterado!", null));
		}
		
		finally {
			manager.close();
		}
	}
}
