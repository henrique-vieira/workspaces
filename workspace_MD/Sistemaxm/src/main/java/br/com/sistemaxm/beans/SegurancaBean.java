package br.com.sistemaxm.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
public class SegurancaBean {

	private String usuario;
	private String senha;
	
public String logar(){
	    FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			this.getRequest().login(this.usuario, this.senha);
			return "/faces/Master?faces-redirect-true";
		} catch (ServletException e) {
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuário ou senha inválidos !", null));
			
			return null;
		}
	}
	
	public String sair() throws ServletException{
		this.getRequest().logout();
		return "/faces/TelaLogin?faces-redirect-true";
		
	}
	
	private HttpServletRequest getRequest(){
		FacesContext context = FacesContext.getCurrentInstance();
		return (HttpServletRequest)context.getExternalContext().getRequest();
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
