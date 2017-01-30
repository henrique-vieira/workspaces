package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.RowEditEvent;

import br.com.sistemaxm.business.CadastroFuncao;
import br.com.sistemaxm.entidades.Funcao;
import br.com.sistemaxm.repository.FuncaoRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class CadastroFuncaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Funcao> funcoes;
	private Funcao funcao;
	
	public CadastroFuncaoBean() {
		this.funcao = new Funcao();
	}
		
	public void listaFuncoes() {
		EntityManager manager = JpaUtil.getEntityManager();		
		
		try {
			FuncaoRepository funcaoRepo = new FuncaoRepository(manager);
			this.funcoes = funcaoRepo.listaFuncao();			
		}
		catch(Exception e) {
			e.getMessage();
		}
		finally {
			manager.close();
		}
		
	}
	
	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		FacesContext context = FacesContext.getCurrentInstance();
		EntityTransaction trx = manager.getTransaction();
		
		try {
			trx.begin();
			CadastroFuncao cadastro = new CadastroFuncao(new FuncaoRepository(manager));
			this.funcao.setDescricao(this.funcao.getDescricao().toUpperCase());
			cadastro.salvar(this.funcao);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Funcão cadastrada com sucesso!",null));
			this.funcao = new Funcao();
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Função não cadastrada!",null));
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
			CadastroFuncao cadastro = new CadastroFuncao(new FuncaoRepository(manager));
			this.funcao = manager.find(Funcao.class, this.funcao.getCodigo());
			cadastro.excluir(funcao);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro excluído com sucesso!",null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não excluído",null));
		}
		finally {
			manager.close();
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		FacesContext context = FacesContext.getCurrentInstance();
		EntityTransaction trx = manager.getTransaction();
		this.funcao = (Funcao)event.getObject();
		
		try {
			trx.begin();
			CadastroFuncao cadastro = new CadastroFuncao(new FuncaoRepository(manager));
			this.funcao.setCodigo(((Funcao)manager.find(Funcao.class, this.funcao.getCodigo())).getCodigo());
			this.funcao.setDescricao(this.funcao.getDescricao().toUpperCase());
			cadastro.editar(funcao);
			this.funcao = new Funcao();
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

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	
	
	
}
