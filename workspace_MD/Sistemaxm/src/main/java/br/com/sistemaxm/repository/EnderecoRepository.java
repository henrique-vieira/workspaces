package br.com.sistemaxm.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.sistemaxm.entidades.Endereco;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class EnderecoRepository {

	EntityManager manager;

	public EnderecoRepository(EntityManager manager) {
		this.manager = manager;
	}  

	
	public Object[] retornaEnderecoPorCep(String cep) {		
		Query query = manager
					.createNativeQuery("SELECT l.log_nome, l.log_complemento, l.cep, b.bai_no as bairro, cid.loc_no as cidade, cid.ufe_sg" + 
									    " FROM log_logradouro l, log_localidade cid, log_bairro b " + 
									    "WHERE l.cep = :cep and l.bai_nu_sequencial_ini = b.bai_nu_sequencial and l.loc_nu_sequencial = cid.loc_nu_sequencial and l.ufe_sg = cid.ufe_sg"
						);
		query.setParameter("cep", cep);
		Object[] result = (Object[]) query.getSingleResult();
		return result;
		 		
	}

}
