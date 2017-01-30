package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.sistemaxm.business.CadastroLocacao;
import br.com.sistemaxm.entidades.ContratoLocacao;
import br.com.sistemaxm.repository.ContratoLocacaoRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class ConsultaLocacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ContratoLocacao contratoLocacao;
	private List<ContratoLocacao> contratosLocados;
	private List<ContratoLocacao> filteredContLoc;
	
	public ConsultaLocacaoBean() {
		this.contratoLocacao = new ContratoLocacao();
	}
	
	public void consultar() {
		EntityManager manager = JpaUtil.getEntityManager();
		ContratoLocacaoRepository contLocRepo = new ContratoLocacaoRepository(manager);
		try {
			this.contratosLocados = contLocRepo.todos();
		}
		finally {
			manager.close();
		}
		
	}

	public ContratoLocacao getContratoLocacao() {
		return contratoLocacao;
	}

	public void setContratoLocacao(ContratoLocacao contratoLocacao) {
		this.contratoLocacao = contratoLocacao;
	}

	public List<ContratoLocacao> getContratosLocados() {
		return contratosLocados;
	}

	public void setContratosLocados(List<ContratoLocacao> contratosLocados) {
		this.contratosLocados = contratosLocados;
	}

	public List<ContratoLocacao> getFilteredContLoc() {
		return filteredContLoc;
	}

	public void setFilteredContLoc(List<ContratoLocacao> filteredContLoc) {
		this.filteredContLoc = filteredContLoc;
	}
	
	public void excluirLocacao() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroLocacao cadastro = new CadastroLocacao(new ContratoLocacaoRepository(manager));
			this.contratoLocacao = manager.find(ContratoLocacao.class, this.contratoLocacao.getCodigo());
			cadastro.excluir(contratoLocacao);
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
