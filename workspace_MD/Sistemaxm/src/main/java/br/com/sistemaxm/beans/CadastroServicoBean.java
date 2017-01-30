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

import br.com.sistemaxm.business.CadastroMarcas;
import br.com.sistemaxm.business.CadastroServico;
import br.com.sistemaxm.entidades.Marca;
import br.com.sistemaxm.entidades.Servico;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.MarcaRepository;
import br.com.sistemaxm.repository.ServicoRepository;

@ManagedBean
@ViewScoped
public class CadastroServicoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Servico servico;
	private List<Servico> servicos;
	
	public CadastroServicoBean() {
		this.servico = new Servico();
	}
	
	public void consultar() {
		EntityManager manager = JpaUtil.getEntityManager();
		try {
			ServicoRepository servicoRepo = new ServicoRepository(manager);
			this.servicos = servicoRepo.todos();
		}
		finally {
			manager.close();
		}
	}
	
	public void cadastrar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroServico cadastro = new CadastroServico(new ServicoRepository(manager));
			cadastro.salvar(servico);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Serviço cadastrado com sucesso!", null));
			this.servico = new Servico();
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cadastrar serviço.", null));
		}
		finally {
			manager.close();
		}
	}

	public void onRowEdit(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.servico = (Servico) event.getObject();
		
		try {
			trx.begin();
			CadastroServico cadastro = new CadastroServico(new ServicoRepository(manager));
			this.servico.setCodigo(((Servico)manager.find(Servico.class, this.servico.getCodigo())).getCodigo());
			cadastro.editar(servico);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Serviço alterado com sucesso!", null));
			this.servico = new Servico();
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao alterar serviço.", null));
		}
		finally {
			manager.close();
		}
	}
	
	public void excluir() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroServico cadastro = new CadastroServico(new ServicoRepository(manager));
			this.servico = manager.find(Servico.class, this.servico.getCodigo());
			cadastro.excluir(servico);
			this.servico = new Servico();
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Serviço excluido com sucesso!",null));
		}
		catch (Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao excluir serviço.",null));
		}
		finally {
			manager.close();
		}
	}
	
	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}
	
	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}
}
