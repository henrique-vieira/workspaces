package br.com.sistemaxm.entidades;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaItens {

	@XmlElement(name="item")
	private List<ItemPeca> itens;
	
	public ListaItens(List<ItemPeca> itens) {
		this.itens = itens;
	}
	
	public ListaItens() {
		
	}
	
	public List<ItemPeca> getItens() {
		return this.itens;
	}
}
