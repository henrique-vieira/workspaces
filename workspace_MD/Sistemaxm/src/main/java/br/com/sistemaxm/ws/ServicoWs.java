package br.com.sistemaxm.ws;

import java.io.Serializable;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.sistemaxm.business.CadastroServico;
import br.com.sistemaxm.entidades.Servico;
import br.com.sistemaxm.entidades.TokenUsuario;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ServicoRepository;

@WebService
public class ServicoWs implements Serializable {

	private static final long serialVersionUID = 1L;

	public ServicoWs() {
		super();
	}

	private List<Servico> listaServicos;
	
	@WebMethod(operationName="TodosOsServicos")
	@WebResult(name="Servicos")
	public ListaServicos getServicos() {
		EntityManager manager = JpaUtil.getEntityManager();
		try {
			ServicoRepository servicoRepo = new ServicoRepository(manager);
			this.listaServicos = servicoRepo.todos();
			return new ListaServicos(this.listaServicos);
		}		
		
		finally {
			manager.close();
		}
	
}
	@WebMethod(operationName="CadastrarServico")
	@WebResult(name="Servico")
	public Servico cadastrarServico(@WebParam(name="tokenUsuario", header=true) TokenUsuario tokenUsuario, @WebParam(name="servico") Servico servico) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		
		try {
			trx.begin();
			CadastroServico cadastro = new CadastroServico(new ServicoRepository(manager));
			cadastro.salvar(servico);
			trx.commit();
		}
		catch(Exception e) {
			trx.rollback();
			e.getMessage();
		}		
		finally {
			manager.close();
		}
		return servico;
	}

	public List<Servico> getListaServicos() {
		return listaServicos;
	}

	public void setListaServicos(List<Servico> listaServicos) {
		this.listaServicos = listaServicos;
	}
	
	
	
	
}
