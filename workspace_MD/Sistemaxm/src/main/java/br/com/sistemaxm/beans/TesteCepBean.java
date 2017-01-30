package br.com.sistemaxm.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;

import br.com.sistemaxm.entidades.Endereco;
import br.com.sistemaxm.repository.EnderecoRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean(name="testeCepBean")
@ViewScoped
public class TesteCepBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Endereco endereco;
	
	public TesteCepBean() {
		this.endereco = new Endereco();
	}
	
	public void buscaEndereco(AjaxBehaviorEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		Object[] out;
		
		try {
			EnderecoRepository enderecoRepo = new EnderecoRepository(manager);
			out = enderecoRepo.retornaEnderecoPorCep(this.endereco.getCep());			
			this.endereco.setLogradouro((String) out[0]);
			this.endereco.setBairro((String) out[3]);
			this.endereco.setCidade((String) out[4]);
			this.endereco.setUf((String) out[5]);
		
		}
		finally {
			manager.close();
		}
	}	
	
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	
}
