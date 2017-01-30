package br.com.sistemaxm.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.sistemaxm.business.CadastroMarcas;
import br.com.sistemaxm.business.NegocioException;
import br.com.sistemaxm.entidades.Marca;
import br.com.sistemaxm.repository.JpaUtil;
import br.com.sistemaxm.repository.MarcaRepository;

@ManagedBean
@ViewScoped
public class CadastroMarcaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Marca marca = new Marca();
	
	
	public void salvar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroMarcas cadastro = new CadastroMarcas(new MarcaRepository(manager));
			cadastro.salvar(this.marca);						
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Marca cadastrada com sucesso!", "Marca cadastrada com sucesso!"));
			this.marca = new Marca();
			trx.commit();
		}
		catch(Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Marca já está cadastrada!",null));
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
			CadastroMarcas cadastro = new CadastroMarcas(new MarcaRepository(manager));
			marca = manager.find(Marca.class, marca.getCodigo());
			cadastro.excluir(marca);
			this.marca = new Marca();
			trx.commit();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro excluido com sucesso!",null));
		}
		catch (Exception e) {
			trx.rollback();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro não pode ser excluido!",null));
		}
		finally {
			manager.close();
		}
	}
	
	public void editar() {
		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			trx.begin();
			CadastroMarcas cadastro = new CadastroMarcas(new MarcaRepository(manager));
			marca = manager.find(Marca.class, this.marca.getDescricao());
			marca.setDescricao(this.marca.getDescricao());
			cadastro.editar(marca);
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

	public Marca getMarca() {
		return marca;
	}


	public void setMarca(Marca marca) {
		this.marca = marca;
	}

		
	
}
