package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.sistemaxm.business.CadastroCotacao;
import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.CotacaoPecas;
import br.com.sistemaxm.entidades.Fornecedor;
import br.com.sistemaxm.entidades.Pessoa;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.CotacaoRepository;
import br.com.sistemaxm.repository.FornecedorRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class CadastroCotacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Carro carro;
	private List<Carro> carros;
	private CotacaoPecas cotacaoPecas;
	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;
	
	public CadastroCotacaoBean() {
		this.carro = new Carro(new Long(0), new String(), new String());
		this.cotacaoPecas = new CotacaoPecas();
		this.fornecedor = new Fornecedor(new Long(0), new Pessoa());
	}
	
	@PostConstruct
	public void listaCarros() {
		EntityManager manager = JpaUtil.getEntityManager();		
		try {
			CarroRepository carroRepo = new CarroRepository(manager);
			this.carros = carroRepo.listaCarros();
		}
		finally {
			manager.close();
		}
	}
	
	public List<Carro> autoCompleteCarro(String busca) {
		List<Carro> auxLista = new ArrayList<Carro>();
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			CarroRepository carroRepo = new CarroRepository(manager);
			this.carros = carroRepo.listaCarros();
			for (Carro c : carros) {
				
				if (c.getPlaca().toLowerCase().startsWith(busca.toLowerCase())) {
					auxLista.add(c);
				}
			}
			return auxLista;
		}
		finally {
			manager.close();
		}
	}
	
	public List<Fornecedor> autoCompleteFornecedor(String busca) {
		List<Fornecedor> auxLista = new ArrayList<Fornecedor>();
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			FornecedorRepository fornecedorRepo = new FornecedorRepository(manager);
			this.fornecedores = fornecedorRepo.nomesFornecedor();
			for (Fornecedor f : fornecedores) {
				if (f.getFornecedor().getNome().toLowerCase().startsWith(busca.toLowerCase())) {
					auxLista.add(f);
				}
			}
			return auxLista;
		}
		finally {
			manager.close();
		}
	}
	
	public void atualizaPrecoTotalListener(AjaxBehaviorEvent e) {	
		this.cotacaoPecas.setValor(this.cotacaoPecas.getValorUnitario()*this.cotacaoPecas.getQtde());
	}
	
	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			this.cotacaoPecas.setCarro(carro);
			this.cotacaoPecas.setFornecedor(fornecedor);
			CadastroCotacao cadastro = new CadastroCotacao(new CotacaoRepository(manager));
			cadastro.salvar(cotacaoPecas);
			trx.commit();
			this.cotacaoPecas = new CotacaoPecas();
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro cadastrado com sucesso!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não cadastrado!",null));
		}
		finally {
			manager.close();
		}
	}
	
	public Carro getCarro() {
		return carro;
	}
	public void setCarro(Carro carro) {
		this.carro = carro;
	}
	public List<Carro> getCarros() {
		return carros;
	}
	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}
	public CotacaoPecas getCotacaoPecas() {
		return cotacaoPecas;
	}
	public void setCotacaoPecas(CotacaoPecas cotacaoPecas) {
		this.cotacaoPecas = cotacaoPecas;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}
	
	
}
