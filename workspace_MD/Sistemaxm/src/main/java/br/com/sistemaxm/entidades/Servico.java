package br.com.sistemaxm.entidades;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Entity implementation class for Entity: Servico
 *
 */
@XmlRootElement
@Entity
@Table(name="servico", uniqueConstraints={@UniqueConstraint(columnNames=("descricao"))})
public class Servico implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private String descricao;	
	//private List<Manutencao> listaManutencoes;

	public Servico() {
		super();
	}   
	
	public Servico(Long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	@Id    
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	@Column(nullable=false, unique=true)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
/*	@OneToMany(mappedBy="servico")	
	public List<Manutencao> getListaManutencoes() {
		return listaManutencoes;
	}

	public void setListaManutencoes(List<Manutencao> listaManutencoes) {
		this.listaManutencoes = listaManutencoes;
	}
*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servico other = (Servico) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
   
}
