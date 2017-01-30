package br.com.sistemaxm.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Aso implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private Long numAso;
	private Date dataExameAdmissional;
	private Date dataExamePeriodico;	
	
	public Aso() {
		
	}
	
	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getNumAso() {
		return numAso;
	}

	public void setNumAso(Long numAso) {
		this.numAso = numAso;
	}
	
	public Date getDataExameAdmissional() {
		return dataExameAdmissional;
	}

	public void setDataExameAdmissional(Date dataExameAdmissional) {
		this.dataExameAdmissional = dataExameAdmissional;
	}	
			
	public Date getDataExamePeriodico() {
		return dataExamePeriodico;
	}

	public void setDataExamePeriodico(Date dataExamePeriodico) {
		this.dataExamePeriodico = dataExamePeriodico;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((numAso == null) ? 0 : numAso.hashCode());
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
		Aso other = (Aso) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (numAso == null) {
			if (other.numAso != null)
				return false;
		} else if (!numAso.equals(other.numAso))
			return false;
		return true;
	}
	
	

}
