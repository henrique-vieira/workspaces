package br.com.sistemaxm.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemaxm.entidades.Manutencao;

public class ManutencaoRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	EntityManager manager;
	
	public ManutencaoRepository(EntityManager manager) {		
		this.manager = manager;
	}

	public ManutencaoRepository() {
		
	}
	
	public List<Manutencao> todos() {
		TypedQuery<Manutencao> query = manager.createQuery("from Manutencao", Manutencao.class);
		return query.getResultList();
	}
	
	public List<Object[]> listaAlertaManutencoes(String servico) {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select c.modelo, c.placa, s.descricao as servico, ip.descricao as item, date_format(m.dataManutencao,'%d/%m/%Y') as data_ultima_manutencao, m.km_troca as km_ultima_troca, c.km as km_atual, m.km_prox_troca");
		sqlString.append(" ");
		sqlString.append("from carro c, manutencao m, servico s, item_peca ip");
		sqlString.append(" ");
		sqlString.append("where c.id = m.cod_carro and m.cod_item = ip.codigo and m.cod_servico = s.codigo and s.descricao = :servico and m.km_prox_troca-1001 <= c.km");
		Query query = (Query) manager.createNativeQuery(sqlString.toString());
		query.setParameter("servico", servico);
		List<Object[]> resultList = (List<Object[]>) query.getResultList();		
		return resultList;
	}
	
	public Manutencao porId(Long id) {
		return this.manager.find(Manutencao.class, id);
	}
	
	public void adicionar(Manutencao manutencao) {
		this.manager.persist(manutencao);
	}
	
	public void editar(Manutencao manutencao) {
		this.manager.merge(manutencao);
	}
	
	public void excluir(Manutencao manutencao) {
		this.manager.remove(manutencao);
	}
}
