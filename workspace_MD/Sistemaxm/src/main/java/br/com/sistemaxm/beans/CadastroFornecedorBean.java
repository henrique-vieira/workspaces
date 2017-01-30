package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.sistemaxm.business.CadastroFornecedor;
import br.com.sistemaxm.entidades.Endereco;
import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.entidades.PessoaJuridica;
import br.com.sistemaxm.enums.TipoFornecedor;
import br.com.sistemaxm.repository.FornecedorRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class CadastroFornecedorBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Fornecedor fornecedor;
	private PessoaJuridica pessoaJuridica;
	private Endereco endereco;
	
	@ManagedProperty(value = "#{testeCepBean}")
	private TesteCepBean testeCepBean;
	
	public CadastroFornecedorBean() {
		this.fornecedor = new Fornecedor();
		this.pessoaJuridica = new PessoaJuridica();
		this.endereco = new Endereco();
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
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
	public TesteCepBean getTesteCepBean() {
		return testeCepBean;
	}

	public void setTesteCepBean(TesteCepBean testeCepBean) {
		this.testeCepBean = testeCepBean;
	}

	public List<SelectItem> getTipoItemListTpFornecedor() {
		List<SelectItem> auxLista = new ArrayList<SelectItem>();
		for (TipoFornecedor tpFornecedor : TipoFornecedor.values()) {
			auxLista.add(new SelectItem(tpFornecedor, tpFornecedor.getValue()));
		}
		return auxLista;
	}
	
	public void buscaCep(AjaxBehaviorEvent event) {
		this.testeCepBean.getEndereco().setCep(this.endereco.getCep());
		this.testeCepBean.buscaEndereco(event);
		this.endereco.setLogradouro(this.testeCepBean.getEndereco().getLogradouro());
		this.endereco.setBairro(this.testeCepBean.getEndereco().getBairro());
		this.endereco.setCidade(this.testeCepBean.getEndereco().getCidade());
		this.endereco.setUf(this.testeCepBean.getEndereco().getUf());
	}
	
	public void cadastrar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroFornecedor cadastro = new CadastroFornecedor(new FornecedorRepository(manager));
			this.pessoaJuridica.setEndereco(endereco);
			this.fornecedor.setFornecedor(pessoaJuridica);
			cadastro.salvar(fornecedor);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Fornecedor cadastrado com sucesso!",null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cadastrar Fornecedor!", null));
		}
		finally {
			manager.close();
		}
	}
	
}
