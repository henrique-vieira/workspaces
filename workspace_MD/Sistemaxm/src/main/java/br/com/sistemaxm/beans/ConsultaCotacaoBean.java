package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.RowEditEvent;

import br.com.sistemaxm.business.CadastroCotacao;
import br.com.sistemaxm.business.CadastroItemPeca;
import br.com.sistemaxm.entidades.CotacaoPecas;
import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.entidades.ItemPeca;
import br.com.sistemaxm.repository.CotacaoRepository;
import br.com.sistemaxm.repository.ItemPecaRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class ConsultaCotacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private CotacaoPecas cotacaoPecas;
	private List<CotacaoPecas> listaCotacao;
	private ItemPeca itemPeca;
	
	
	public ConsultaCotacaoBean() {		
		this.itemPeca = new ItemPeca();
		this.cotacaoPecas = new CotacaoPecas();
	}
	
	public void consultar() {
		EntityManager manager = JpaUtil.getEntityManager();
		try {
			CotacaoRepository cotacaoRepo = new CotacaoRepository(manager);
			this.listaCotacao = cotacaoRepo.todos();
		}
		finally {
			manager.close();
		}
	}

	public CotacaoPecas getCotacaoPecas() {
		return cotacaoPecas;
	}

	public void setCotacaoPecas(CotacaoPecas cotacaoPecas) {
		this.cotacaoPecas = cotacaoPecas;
	}
		
	public ItemPeca getItemPeca() {
		return itemPeca;
	}

	public void setItemPeca(ItemPeca itemPeca) {
		this.itemPeca = itemPeca;
	}

	public List<CotacaoPecas> getListaCotacao() {
		return listaCotacao;
	}

	public void setListaCotacao(List<CotacaoPecas> listaCotacao) {
		this.listaCotacao = listaCotacao;
	}
	
	public void incluirEstoque() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println("passando pelo bean");
		try {
			trx.begin();
			CadastroItemPeca cadastro = new CadastroItemPeca(new ItemPecaRepository(manager));
			this.itemPeca.setDescricao(this.cotacaoPecas.getItem());
			this.itemPeca.setDataEntrada(new Date(System.currentTimeMillis()));
			this.itemPeca.setQtde(this.cotacaoPecas.getQtde());
			this.itemPeca.setQtdeEntrada(this.cotacaoPecas.getQtde());
			this.itemPeca.setFornecedor(this.cotacaoPecas.getFornecedor());
			this.itemPeca.setValorUnitario(this.cotacaoPecas.getValorUnitario());
			this.itemPeca.setValor(this.cotacaoPecas.getValor());			
			cadastro.salvar(this.itemPeca);			
			trx.commit();
			this.itemPeca = new ItemPeca();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Item inserido no estoque!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Item não inserido no estoque.", null));
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
			CadastroCotacao cadastro = new CadastroCotacao(new CotacaoRepository(manager));
			this.cotacaoPecas = manager.find(CotacaoPecas.class, this.cotacaoPecas.getCodigo());
			cadastro.excluir(cotacaoPecas);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro excluido com sucesso!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não excluído!", null));
		}
		finally {
			manager.close();
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.cotacaoPecas = (CotacaoPecas) event.getObject();
		
		try {
			trx.begin();
			CadastroCotacao cadastro = new CadastroCotacao(new CotacaoRepository(manager));
			this.cotacaoPecas.setCodigo(((CotacaoPecas) manager.find(CotacaoPecas.class, this.cotacaoPecas.getCodigo())).getCodigo());
			cadastro.editar(cotacaoPecas);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro alterado com sucesso!", null));
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
