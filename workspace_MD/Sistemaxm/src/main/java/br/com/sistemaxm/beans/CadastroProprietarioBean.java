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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.sistemaxm.business.CadastroClientes;
import br.com.sistemaxm.business.CadastroLocacao;
import br.com.sistemaxm.business.CadastroProprietarios;
import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.ContratoLocacao;
import br.com.sistemaxm.entidades.Endereco;
import br.com.sistemaxm.entidades.PessoaFisica;
import br.com.sistemaxm.entidades.PessoaJuridica;
import br.com.sistemaxm.entidades.Proprietario;
import br.com.sistemaxm.enums.Sexo;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.ContratoLocacaoRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ProprietarioRepository;

@ManagedBean
@ViewScoped
public class CadastroProprietarioBean implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	private Proprietario proprietario;	
	private PessoaFisica pessoaFisica;
	private PessoaJuridica pessoaJuridica;
	private Endereco endereco;
	
	@ManagedProperty(value = "#{testeCepBean}")
	private TesteCepBean testeCepBean;
	
	public CadastroProprietarioBean() {
		this.proprietario = new Proprietario();
		this.pessoaFisica = new PessoaFisica();
		this.pessoaJuridica = new PessoaJuridica();
		this.endereco = new Endereco();
					
		
	}
	
	public List<SelectItem> getTipoItemListSexo() {
		List<SelectItem> auxLista = new ArrayList<SelectItem>();
		for (Sexo sexo : Sexo.values()) {
			auxLista.add(new SelectItem(sexo, sexo.getValue()));
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
		
	public Endereco getEndereco() {
		return endereco;
	}


	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}



	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public Proprietario getProprietario() {
		return proprietario;
	}

	public void setCliente(Proprietario proprietario) {
		this.proprietario = proprietario;
	}
	
		
	public TesteCepBean getTesteCepBean() {
		return testeCepBean;
	}

	public void setTesteCepBean(TesteCepBean testeCepBean) {
		this.testeCepBean = testeCepBean;
	}

	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}

	public void vcePjPf(ValueChangeEvent e) {
		System.out.println(e.getNewValue());
		
		String s = (String) e.getNewValue();
		
		if (s.equals("pj")) {
			this.proprietario.setPf(false);
			this.proprietario.setPj(true);
		} else {
			this.proprietario.setPf(true);
			this.proprietario.setPj(false);
		}
	}
	
	
	public void salvarPf() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();		
			this.pessoaFisica.setEndereco(endereco);
			this.proprietario.setProprietario(pessoaFisica);
			CadastroProprietarios cadastro = new CadastroProprietarios(new ProprietarioRepository(manager));
			cadastro.salvar(proprietario);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Proprietario cadastrado com sucesso!",null));
			this.pessoaFisica = new PessoaFisica();
			this.proprietario = new Proprietario();
			trx.commit();
						
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cadastrar proprietário.",null));
		}
		finally {
			manager.close();

		}
	}
	
	public void salvarPj() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();	
			this.pessoaJuridica.setEndereco(endereco);
			this.proprietario.setProprietario(pessoaJuridica);
			CadastroProprietarios cadastro = new CadastroProprietarios(new ProprietarioRepository(manager));
			cadastro.salvar(proprietario);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Proprietario cadastrado com sucesso!",null));
			this.pessoaJuridica = new PessoaJuridica();
			this.proprietario = new Proprietario();
			trx.commit();
						
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Proprietario não cadastrado!",null));
		}
		finally {
			manager.close();
			
		}
		
		
	}
	
	
	}
