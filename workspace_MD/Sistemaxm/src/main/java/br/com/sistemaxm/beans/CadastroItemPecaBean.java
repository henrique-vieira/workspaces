package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.sistemaxm.business.CadastroItemPeca;
import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.entidades.ItemPeca;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.repository.FornecedorRepository;
import br.com.sistemaxm.repository.ItemPecaRepository;
import br.com.sistemaxm.repository.JpaUtil;

// Bean do ESTOQUE!
@ManagedBean
@ViewScoped
public class CadastroItemPecaBean implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	private ItemPeca itemPeca;
	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;

	public CadastroItemPecaBean() {
		this.itemPeca = new ItemPeca();
		this.fornecedor = new Fornecedor(new Long(0), new Pessoa());
	}
	
	
	public ItemPeca getItemPeca() {
		return itemPeca;
	}

	public void setItemPeca(ItemPeca itemPeca) {
		this.itemPeca = itemPeca;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}
	
	public List<Fornecedor> autoCompleteFornecedor(String busca) {
		List<Fornecedor> auxLista = new ArrayList<Fornecedor>();
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			FornecedorRepository fornecedorRepo = new FornecedorRepository(manager);
			this.fornecedores = fornecedorRepo.nomesFornecedor();
			for (Fornecedor f : fornecedores) {
				if (f.getFornecedor().getNome().toLowerCase().startsWith(busca.toLowerCase())) {
					auxLista.add(f);
				}
			}
			return auxLista;
		}
		finally {
			manager.close();
		}
	}
	
	public void atualizaPrecoTotalListener(AjaxBehaviorEvent e) {	
		this.itemPeca.setValor(this.itemPeca.getValorUnitario()*this.itemPeca.getQtdeEntrada());
	}
	
	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			this.itemPeca.setFornecedor(fornecedor);
			this.itemPeca.setQtde(this.itemPeca.getQtdeEntrada());
			CadastroItemPeca cadastro = new CadastroItemPeca(new ItemPecaRepository(manager));
			cadastro.salvar(itemPeca);
			trx.commit();
			this.itemPeca = new ItemPeca();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Item/Peça cadastrado com sucesso!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cadastrar Item/Peça.", null));
		}
		
		finally {
			manager.close();
		}
		
	}
}
