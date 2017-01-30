package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import org.primefaces.model.DualListModel;

import br.com.sistemaxm.entidades.ItemPeca;
import br.com.sistemaxm.entidades.Servico;
import br.com.sistemaxm.repository.ItemPecaRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ServicoRepository;

@ManagedBean
@ViewScoped
public class PickListBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private DualListModel<ItemPeca> itemPecas;
	private DualListModel<Servico> servicos;

	public PickListBean() {
		
	}
	
	@PostConstruct
	public void init() {
		carregaPickListItemPeca();
		carregaPickListServicos();
	}
	
	// carrega pickList de peças
	public void carregaPickListItemPeca() {
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			ItemPecaRepository itemPecaRepo = new ItemPecaRepository(manager);
			List<ItemPeca> itensSource = new ArrayList<ItemPeca>();
			List<ItemPeca> itensTarget = new ArrayList<ItemPeca>();			
			itensSource = itemPecaRepo.itensCombo();
			this.itemPecas = new DualListModel<ItemPeca>(itensSource, itensTarget);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			manager.close();
		}
	}
	// Carrega pickList de serviços
	public void carregaPickListServicos() {
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			ServicoRepository servicoRepo = new ServicoRepository(manager);
			List<Servico> servicosSource = new ArrayList<Servico>();
			List<Servico> servicosTarget = new ArrayList<Servico>();
			servicosSource = servicoRepo.todos();
			this.servicos = new DualListModel<Servico>(servicosSource, servicosTarget);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			manager.close();
		}
	}

	public DualListModel<ItemPeca> getItemPecas() {
		return itemPecas;
	}

	public void setItemPecas(DualListModel<ItemPeca> itemPecas) {
		this.itemPecas = itemPecas;
	}

	public DualListModel<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(DualListModel<Servico> servicos) {
		this.servicos = servicos;
	}
	
	

}
