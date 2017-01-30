package br.com.sistemaxm.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.ToStringBuilder;

import br.com.sistemaxm.enums.TipoFornecedor;

@Entity
public class Fornecedor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private Pessoa fornecedor;
	private TipoFornecedor tipo;
	
	public Fornecedor() {
		
	}
	
	public Fornecedor(Long codigo, Pessoa fornecedor) {
		this.codigo = codigo;
		this.fornecedor = fornecedor;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="cod_pessoa")
	public Pessoa getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Pessoa fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	@Enumerated(EnumType.STRING)
	public TipoFornecedor getTipo() {
		return tipo;
	}

	public void setTipo(TipoFornecedor tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Fornecedor other = (Fornecedor) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
	
}
