package br.com.sistemaxm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.ItemPeca;

public class ItemPecaRepository {

	EntityManager manager;
	
	public ItemPecaRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public ItemPecaRepository() {
		
	}

	public ItemPeca porId(Long id) {
		return this.manager.find(ItemPeca.class, id);
	}
	
	public List<ItemPeca> todos() {
		TypedQuery<ItemPeca> query = manager.createQuery("from ItemPeca",ItemPeca.class);
		return query.getResultList();
	}
	
	public List<ItemPeca> itensCombo() {
		TypedQuery<ItemPeca> query = manager.createQuery("Select new ItemPeca(ip.codigo, ip.descricao) from ItemPeca ip", ItemPeca.class);
		return query.getResultList();
	}
	
	public void adicionar(ItemPeca itemPeca) {
		this.manager.persist(itemPeca);
	}
	
	public void editar(ItemPeca itemPeca) {
		this.manager.merge(itemPeca);
	}
	
	public void excluir(ItemPeca itemPeca) {
		this.manager.remove(itemPeca);
	}
	
}
