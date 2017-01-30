package br.com.sistemaxm.ws;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.sistemaxm.entidades.Servico;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaServicos {

	@XmlElement(name="servico")
	private List<Servico> servicos;
		
	public ListaServicos() {
		super();
	}
	
	public ListaServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}
	
	
	
}
