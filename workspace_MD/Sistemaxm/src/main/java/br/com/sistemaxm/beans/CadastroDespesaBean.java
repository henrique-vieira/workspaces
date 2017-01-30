package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.RowEditEvent;

import br.com.sistemaxm.business.CadastroClientes;
import br.com.sistemaxm.business.CadastroDespesa;
import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.Despesa;
import br.com.sistemaxm.entidades.PessoaFisica;
import br.com.sistemaxm.enums.Cambio;
import br.com.sistemaxm.enums.TipoDespesa;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.DespesaRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean
@ViewScoped
public class CadastroDespesaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Despesa despesa;
	private List<Despesa> listaDespesas;
	private List<Carro> carros;
	private Carro carro;

	public CadastroDespesaBean() {
		this.setDespesa(new Despesa());
	}
	
	public List<Carro> autoCompleteCarro(String busca) {
		List<Carro> auxLista = new ArrayList<Carro>();
		EntityManager manager = JpaUtil.getEntityManager();

		try {
			CarroRepository carroRepo = new CarroRepository(manager);
			this.carros = carroRepo.listaCarros();
			for (Carro c : carros) {
				if (c.getPlaca().toLowerCase().startsWith(busca.toLowerCase())
						|| c.getModelo().toLowerCase()
								.startsWith(busca.toLowerCase())) {
					auxLista.add(c);
				}
			}
			return auxLista;
		} finally {
			manager.close();
		}
	}
	
	public void listaDespesaBean() {
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			DespesaRepository despesaRepo = new DespesaRepository(manager);
			this.listaDespesas = despesaRepo.todos();
		}
		catch(Exception e) {
			e.getMessage();
		}
		finally {
			manager.close();
		}
	}
	
	public List<SelectItem> getTipoItemDespesa() { 
		List<SelectItem> auxLista = new ArrayList<SelectItem>();
		for (TipoDespesa despesaEnum : TipoDespesa.values()) {
			auxLista.add(new SelectItem(despesaEnum, despesaEnum.getValue()));
		}
		return auxLista;
	}
	
	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			this.despesa.setCarro(carro);
			CadastroDespesa cadastro = new CadastroDespesa(new DespesaRepository(manager));
			cadastro.salvar(despesa);
			trx.commit();
			this.despesa = new Despesa();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Despesa cadastrada com sucesso!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cadastrar despesa, verifique se não há registro com mesma data e descrição !", null));
		}
		finally {
			manager.close();
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.despesa = (Despesa) event.getObject();
		
		try {
			trx.begin();
			CadastroDespesa cadastro = new CadastroDespesa(new DespesaRepository(manager));
			this.despesa.setCodigo(((Despesa)manager.find(Despesa.class, despesa.getCodigo())).getCodigo());
			cadastro.editar(this.despesa);
			trx.commit();
			this.despesa = new Despesa();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro editado com sucesso!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não editado!", null));
		}
		
		finally {
			manager.close();
		}
	}
	
	public void excluir() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroDespesa cadastro = new CadastroDespesa(new DespesaRepository(manager));
			this.despesa = manager.find(Despesa.class, this.despesa.getCodigo());
			cadastro.excluir(despesa);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro excluído com sucesso!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro ao excluir registro.", null));
		}
		finally {
			manager.close();
		}
	}

	public Despesa getDespesa() {
		return despesa;
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	public List<Despesa> getListaDespesas() {
		return listaDespesas;
	}

	public void setListaDespesas(List<Despesa> listaDespesas) {
		this.listaDespesas = listaDespesas;
	}

	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	
}
