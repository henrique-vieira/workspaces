package br.com.sistemaxm.entidades;

import javax.persistence.EntityManager;

import br.com.sistemaxm.repository.ContratoLocacaoRepository;
import br.com.sistemaxm.repository.JpaUtil;

public class TesteNumLocacao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManager manager = JpaUtil.getEntityManager();
		ContratoLocacaoRepository contratoLocRepo = new ContratoLocacaoRepository(manager);
		
		try {
			Long max = contratoLocRepo.numLocacao();
			System.out.println(max+1);
		}
		finally {
			manager.close();
		}
	}

}
