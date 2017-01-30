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
import br.com.sistemaxm.business.CadastroReceita;
import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.Cliente;
import br.com.sistemaxm.entidades.Despesa;
import br.com.sistemaxm.entidades.PessoaFisica;
import br.com.sistemaxm.entidades.Receita;
import br.com.sistemaxm.enums.Cambio;
import br.com.sistemaxm.enums.TipoDespesa;
import br.com.sistemaxm.enums.TipoReceita;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ClienteRepository;
import br.com.sistemaxm.repository.DespesaRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ReceitaRepository;

@ManagedBean
@ViewScoped
public class CadastroReceitaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Receita receita;
	private List<Receita> listaReceitas;
	private List<Carro> carros;
	private Carro carro;
	private Cliente cliente;
	private List<Cliente> clientes;

	public CadastroReceitaBean() {
		this.setReceita(new Receita());
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
	
	public List<Cliente> autoCompleteCliente(String busca) {
		List<Cliente> auxLista = new ArrayList<Cliente>();
		EntityManager manager = JpaUtil.getEntityManager();

		try {
			ClienteRepository clienteRepo = new ClienteRepository(manager);
			this.clientes = clienteRepo.nomesCliente();
			for (Cliente c : clientes) {
				if (c.getCliente().getNome().toUpperCase()
						.startsWith(busca.toUpperCase())) {
					auxLista.add(c);
				}
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			manager.close();
		}
		return auxLista;

	}
	
	public void listaReceitaBean() {
		EntityManager manager = JpaUtil.getEntityManager();
		
		try {
			ReceitaRepository receitaRepo = new ReceitaRepository(manager);
			this.listaReceitas = receitaRepo.todos();
		}
		catch(Exception e) {
			e.getMessage();
		}
		finally {
			manager.close();
		}
	}
	
	public List<SelectItem> getTipoItemReceita() { 
		List<SelectItem> auxLista = new ArrayList<SelectItem>();
		for (TipoReceita receitaEnum : TipoReceita.values()) {
			auxLista.add(new SelectItem(receitaEnum, receitaEnum.getValue()));
		}
		return auxLista;
	}
	
	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			this.receita.setCarro(carro);
			CadastroReceita cadastro = new CadastroReceita(new ReceitaRepository(manager));
			cadastro.salvar(receita);
			trx.commit();
			this.receita = new Receita();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Receita cadastrada com sucesso!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao cadastrar receita, verifique se não há registro com mesma data e descrição !", null));
		}
		finally {
			manager.close();
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.receita = (Receita) event.getObject();
		
		try {
			trx.begin();
			CadastroReceita cadastro = new CadastroReceita(new ReceitaRepository(manager));
			this.receita.setCodigo(((Receita)manager.find(Receita.class, receita.getCodigo())).getCodigo());
			cadastro.editar(this.receita);
			trx.commit();
			this.receita = new Receita();
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
			CadastroReceita cadastro = new CadastroReceita(new ReceitaRepository(manager));
			this.receita = manager.find(Receita.class, this.receita.getCodigo());
			cadastro.excluir(receita);
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

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public List<Receita> getListaReceitas() {
		return listaReceitas;
	}

	public void setListaReceitas(List<Receita> listaReceitas) {
		this.listaReceitas = listaReceitas;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	
}
