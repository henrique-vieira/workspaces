package com.vibratecnologia.repository.infra;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.vibratecnologia.model.Lancamento;
import com.vibratecnologia.repository.Lancamentos;

public class LancamentosHibernate implements Lancamentos{
	
	private Session session;
	
	public LancamentosHibernate(Session session){
		this.session = session;
	}

	@Override
	public List<Lancamento> todos() {
		
		return session.createCriteria(Lancamento.class).addOrder(Order.desc("dataVencimento")).list();
	}

	@Override
	public Lancamento guardar(Lancamento lancamento) {
		
		return (Lancamento) session.merge(lancamento);
	}

	@Override
	public void remover(Lancamento lancamento) {
		this.session.delete(lancamento);
		
	}

	@Override
	public Lancamento comDadosIguais(Lancamento lancamento) {
		// Faz a verificação se o lançamento tem dados semelhantes a algum outro
		
		return (Lancamento) this.session.createCriteria(Lancamento.class)
				.add(Restrictions.eq("tipo", lancamento.getTipo()))
				.add(Restrictions.eq("pessoa", lancamento.getPessoa()))
				.add(Restrictions.ilike("descricao", lancamento.getDescricao()))
				.add(Restrictions.eq("valor", lancamento.getValor()))
				.add(Restrictions.eq("dataVencimento", lancamento.getDataVencimento()))
				.uniqueResult();
	}

	@Override
	public Lancamento porCodigo(Integer codigo) {
		// TODO Auto-generated method stub
		return (Lancamento) this.session.get(Lancamento.class, codigo);
	}
	
}
