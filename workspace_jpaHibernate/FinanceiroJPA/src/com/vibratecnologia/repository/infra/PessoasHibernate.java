package com.vibratecnologia.repository.infra;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.vibratecnologia.model.Pessoa;
import com.vibratecnologia.repository.Pessoas;

public class PessoasHibernate implements Pessoas{
	
	private Session session;
	
	public PessoasHibernate(Session session){
		this.session = session;
	}

	@Override
	public List<Pessoa> todas() {
		// TODO Auto-generated method stub
		return session.createCriteria(Pessoa.class).addOrder(Order.asc("nome")).list();
	}

	@Override
	public Pessoa porCodigo(Integer codigo) {
		// TODO Auto-generated method stub
		return (Pessoa) session.get(Pessoa.class, codigo);
	}
	
}
