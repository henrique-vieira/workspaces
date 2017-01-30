package br.com.sistemaxm.entidades;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.sistemaxm.repository.EnderecoRepository;
import br.com.sistemaxm.repository.JpaUtil;

public class TesteCep {

	public static void main(String[] args) {
		EntityManager manager = JpaUtil.getEntityManager();		
		Object[] out;
		String logradouro;
		String cep;
		String complemento;
		String bairro;
		String cidade;
		String uf;
		//Endereco endereco = null;
		 try {
			 EnderecoRepository enderecoRepo = new EnderecoRepository(manager);
			 //retornoCep = enderecoRepo.retornaEnderecoPorCep("60810720");
			 //System.out.println(retornoCep);
			 out = enderecoRepo.retornaEnderecoPorCep("60810720");
			 //endereco.setLogradouro(out.get(0).toString());
			 logradouro = (String) out[0];			 
			 complemento = (String) out[1];
			 cep = (String) out[2];
			 bairro = (String) out[3];
			 cidade = (String) out[4];
			 uf = (String) out[5];
			 System.out.println(logradouro + " " + complemento + " " + cep + " " + bairro + " " + cidade + " " + uf);
		 }
		 finally {
			 manager.close();
		 }

	}

}
