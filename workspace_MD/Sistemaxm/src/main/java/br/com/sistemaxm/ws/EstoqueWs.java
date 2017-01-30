package br.com.sistemaxm.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.sistemaxm.business.CadastroItemPeca;
import br.com.sistemaxm.entidades.ItemPeca;
import br.com.sistemaxm.entidades.ListaItens;
import br.com.sistemaxm.repository.ItemPecaRepository;
import br.com.sistemaxm.repository.JpaUtil;

@WebService
public class EstoqueWs {

	private List<ItemPeca> listaItemPeca;
	
	public EstoqueWs() {
		
	}
	
	@WebMethod(operationName="todosOsItens")
	@WebResult(name="itens")
	public ListaItens getItens() {
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			ItemPecaRepository itemPecaRepo = new ItemPecaRepository(manager);
			this.listaItemPeca = itemPecaRepo.itensCombo();
			return new ListaItens(this.listaItemPeca);
		}		
		
		finally {
			manager.close();
		}
				
	}
	
	@WebMethod(operationName="CadastrarItem")
	@WebResult(name="item")
	public ItemPeca cadastrarItem(@WebParam(name="item")ItemPeca itemPeca) {
		
		System.out.println("cadastrando um item");
		
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		
		try {
			trx.begin();
			CadastroItemPeca cadastro = new CadastroItemPeca(new ItemPecaRepository(manager));
			cadastro.salvar(itemPeca);
			trx.commit();			
		}		
		
		catch(Exception e) {
			trx.rollback();
			e.getMessage();			
		}
		
		finally {
			manager.close();
		}
		return itemPeca;
				
	}

	public void setItens(List<ItemPeca> listaItemPeca) {
		this.listaItemPeca = listaItemPeca;
	}

	public List<ItemPeca> getListaItemPeca() {
		return listaItemPeca;
	}

	public void setListaItemPeca(List<ItemPeca> listaItemPeca) {
		this.listaItemPeca = listaItemPeca;
	}
	
	

}
