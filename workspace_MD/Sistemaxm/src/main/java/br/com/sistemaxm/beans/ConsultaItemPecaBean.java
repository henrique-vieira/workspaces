package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import br.com.sistemaxm.business.CadastroItemPeca;
import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.entidades.ItemPeca;
import br.com.sistemaxm.repository.ItemPecaRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class ConsultaItemPecaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ItemPeca itemPeca;
	private List<Fornecedor> fornecedores;
	private List<ItemPeca> itemPecas;
	
		
	public ConsultaItemPecaBean() {
		this.itemPeca = new ItemPeca();
	}
	
	@PostConstruct
	public void consultar() {
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			ItemPecaRepository itemPecaRepo = new ItemPecaRepository(manager);
			this.itemPecas = itemPecaRepo.todos();
		}
		finally {
			manager.close();
		}
	}
	
	public ItemPeca getItemPeca() {
		return itemPeca;
	}

	public void setItemPeca(ItemPeca itemPeca) {
		this.itemPeca = itemPeca;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}
	
	
	public List<ItemPeca> getItemPecas() {
		return itemPecas;
	}

	public void setItemPecas(List<ItemPeca> itemPecas) {
		this.itemPecas = itemPecas;
	}
		
	public void onRowEditView(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.itemPeca = (ItemPeca) event.getObject();
		
		try {
			trx.begin();
			CadastroItemPeca cadastro = new CadastroItemPeca(new ItemPecaRepository(manager));
			this.itemPeca.setCodigo(((ItemPeca)manager.find(ItemPeca.class, this.itemPeca.getCodigo())).getCodigo());
			cadastro.editar(this.itemPeca);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro alterado com sucesso!", null));
			this.itemPeca = new ItemPeca();
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
