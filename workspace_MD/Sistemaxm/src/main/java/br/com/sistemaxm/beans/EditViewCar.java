package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import br.com.sistemaxm.business.CadastroCarros;
import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.repository.CarroRepository;
import br.com.sistemaxm.repository.JpaUtil;

@ManagedBean(name="dtEditViewCar")
@ViewScoped
public class EditViewCar implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Carro> carros;
	CarroRepository carroRepo;
	private Carro carro = new Carro();
	

	public void init() {
		this.carros = carroRepo.todos();
		
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
	
	public void onRowEdit(RowEditEvent event) {		
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.carro = (Carro) event.getObject();
		
		try {
			trx.begin();
			CadastroCarros cadastro = new CadastroCarros(new CarroRepository(manager));
			this.carro.setId(((Carro)manager.find(Carro.class, carro.getId())).getId());			
			cadastro.editar(this.carro);
			this.carro = new Carro();
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro editado com sucesso!",null));
		}
		catch (Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não editado!",null));
		}
		finally {
			manager.close();
		}
	}
	
	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		
		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro alterado", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}
	}
	
	public String decodeBoolean(boolean value) {
		if (value=true) {
			return "Sim";
		}
		else {
			return "Não";
		}
	}
}
