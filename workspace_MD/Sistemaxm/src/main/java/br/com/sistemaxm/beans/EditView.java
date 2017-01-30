package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import br.com.sistemaxm.business.CadastroMarcas;
import br.com.sistemaxm.entidades.Marca;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.MarcaRepository;

@ManagedBean(name="dtEditView")
@ViewScoped
public class EditView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Marca> marcas;	
	MarcaRepository marcaRepo;
	private Marca marca = new Marca();
	
	public void init() {
	
		this.marcas = marcaRepo.todos();
	}

	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}
	
		
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public void onRowEdit(RowEditEvent event) {		
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		this.marca = (Marca) event.getObject();
		
		try {
			trx.begin();
			CadastroMarcas cadastro = new CadastroMarcas(new MarcaRepository(manager));
			this.marca.setCodigo(((Marca)manager.find(Marca.class, marca.getCodigo())).getCodigo());
			marca.setDescricao(marca.getDescricao());
			cadastro.editar(this.marca); // o thsi.marca nao ta sendo setado valor
			this.marca = new Marca();
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
}
