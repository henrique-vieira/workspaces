package br.com.sistemaxm.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.Marca;

public class CarroRepository implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	public CarroRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public CarroRepository() {
		
	}
	
	public Carro porId(Long id) {
		return manager.find(Carro.class, id);
	}
	
	public List<Carro> veiculosCombo() {
		TypedQuery<Carro> query = manager.createQuery("select new Carro(c.id, c.modelo, c.placa, c.km) "
					+ "from Carro c where c.id not in (select cl.carro from ContratoLocacao cl where cl.dataFimLocacao > sysdate())", Carro.class);
		return query.getResultList();
	}
	
	public List<Carro> modelosDistintos() {
		TypedQuery<Carro> query = manager.createQuery("Select distinct new Carro(c.id, c.modelo, c.km) from Carro c", Carro.class);
		return query.getResultList();
	}
	
	public List<Carro> listaCarros() {
		TypedQuery<Carro> query = manager.createQuery("select new Carro(c.id, c.modelo, c.placa) from Carro c order by c.modelo asc", Carro.class);
		return query.getResultList();
	}
	
	public List<Carro> todos() {
		TypedQuery<Carro> query = manager.createQuery("from Carro", Carro.class);
		return query.getResultList();
	}
	
	public List<Carro> carrosLicenciamento(int mesVencimento) {
		TypedQuery<Carro> query = manager.createQuery("from Carro c where c.calendarioLicenciamento.mesVencimento = :mesVencimento", Carro.class);
		query.setParameter("mesVencimento", mesVencimento);
	    return query.getResultList();
	}
	
	public void adicionar(Carro carro) {
		this.manager.persist(carro);
	}

	public void excluir(Carro carro) {
		this.manager.remove(carro);
	}
	
	public void editar(Carro carro) {
		this.manager.merge(carro);
	}
}
