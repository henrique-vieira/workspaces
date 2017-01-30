package br.com.sistemaxm.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.CalendarioLicenciamento;

public class CalendarioLicenciamentoRepository implements Serializable {

	private static final long serialVersionUID = 1L;
   
	EntityManager manager;
	
	public CalendarioLicenciamentoRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public CalendarioLicenciamentoRepository() {
		
	}
	
	public CalendarioLicenciamento porId(Long id) {
		return this.manager.find(CalendarioLicenciamento.class,id);
	}
	
	public List<CalendarioLicenciamento> todos() {
		TypedQuery<CalendarioLicenciamento> query = manager.createQuery("from CalendarioLicenciamento", CalendarioLicenciamento.class);
		return query.getResultList();
	}
	
	public List<Integer> mesVencimento() {
		TypedQuery<Integer> query = manager.createQuery("Select distinct(mesVencimento) from CalendarioLicenciamento", Integer.class);
		return query.getResultList();
	}
	
	public CalendarioLicenciamento buscaLicenciamentoPorPlaca(char finalPlaca) {
		try {
			TypedQuery<CalendarioLicenciamento> query = manager.createQuery("from CalendarioLicenciamento where finalPlaca = :finalPlaca", CalendarioLicenciamento.class);
			query.setParameter("finalPlaca", finalPlaca);
			return query.getSingleResult();
		}
		catch(Exception e) {
			e.getMessage();
		}
		return null;
		
	}

}
