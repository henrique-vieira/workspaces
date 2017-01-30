package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.RowEditEvent;

import br.com.sistemaxm.business.CadastroAbastecimentoSemJornada;
import br.com.sistemaxm.business.CadastroContrato;
import br.com.sistemaxm.entidades.AbastecimentoSemJornada;
import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.Contrato;
import br.com.sistemaxm.repository.AbastecimentoSemJornadaRepository;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.ContratoRepository;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.ServicoRepository;

@ManagedBean
@ViewScoped
public class CadastroAbastecimentoSemJornadaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private AbastecimentoSemJornada abastecimentoSemJornada;
	private List<Carro> carros;
	private List<AbastecimentoSemJornada> listaAbastSemJornada;
	private Carro carro;

	public CadastroAbastecimentoSemJornadaBean() {
		this.carro = new Carro(new Long(0), new String(), new String());
		this.abastecimentoSemJornada = new AbastecimentoSemJornada();
	}
	
	public void consultar() {
		EntityManager manager = JpaUtil.getEntityManager();
		try {
			AbastecimentoSemJornadaRepository abastRepo = new AbastecimentoSemJornadaRepository(manager);
			this.listaAbastSemJornada = abastRepo.todos();
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
			
			for (Carro car : this.carros) {
				if ((car.getModelo().toUpperCase().startsWith(busca.toUpperCase())) 
						|| (car.getPlaca().toUpperCase().startsWith(busca.toUpperCase()))) {
							auxLista.add(car);
				}
			}
		}
		catch(Exception e) {
			e.getMessage();
		}
		finally {
			manager.close();
		}
		return auxLista;
	}
	
	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroAbastecimentoSemJornada cadastro = new CadastroAbastecimentoSemJornada(new AbastecimentoSemJornadaRepository(manager));
			this.abastecimentoSemJornada.setCarro(this.carro);
			cadastro.salvar(this.abastecimentoSemJornada);
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro cadastrado com sucesso!", null));
			this.abastecimentoSemJornada = new AbastecimentoSemJornada();
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não cadastrado!", null));
		}
		finally {
			manager.close();
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		EntityManager manager = JpaUtil.getEntityManager();
		FacesContext context = FacesContext.getCurrentInstance();
		EntityTransaction trx = manager.getTransaction();
		this.abastecimentoSemJornada = (AbastecimentoSemJornada)event.getObject();
		
		try {
			trx.begin();
			CadastroAbastecimentoSemJornada abastecimento = new CadastroAbastecimentoSemJornada(new AbastecimentoSemJornadaRepository(manager));
			this.abastecimentoSemJornada.setCodigo(((AbastecimentoSemJornada)manager.find(AbastecimentoSemJornada.class, this.abastecimentoSemJornada.getCodigo())).getCodigo());			
			abastecimento.editar(abastecimentoSemJornada);
			this.abastecimentoSemJornada = new AbastecimentoSemJornada();
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro editado com sucesso!", null));
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não editado.", null));
		}
		finally {
			manager.close();
		}
	}
	
	public void atualizaKmPercorridoTotal(AjaxBehaviorEvent event) {
		this.abastecimentoSemJornada.setKmPercorrido(this.abastecimentoSemJornada.getKmAbastecimento()-this.abastecimentoSemJornada.getKmAnterior());
		this.abastecimentoSemJornada.getKmPercorrido();
	}
	
	public void atualizaMediaGasolinaPorLitro(AjaxBehaviorEvent event) {	
		this.abastecimentoSemJornada.setMedia(this.abastecimentoSemJornada.getKmPercorrido()/this.abastecimentoSemJornada.getLitros());
		Math.round(this.abastecimentoSemJornada.getMedia());
	}


	public AbastecimentoSemJornada getAbastecimentoSemJornada() {
		return abastecimentoSemJornada;
	}

	public void setAbastecimentoSemJornada(
			AbastecimentoSemJornada abastecimentoSemJornada) {
		this.abastecimentoSemJornada = abastecimentoSemJornada;
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

	public List<AbastecimentoSemJornada> getListaAbastSemJornada() {
		return listaAbastSemJornada;
	}

	public void setListaAbastSemJornada(
			List<AbastecimentoSemJornada> listaAbastSemJornada) {
		this.listaAbastSemJornada = listaAbastSemJornada;
	}
	
	

}
