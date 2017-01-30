package br.com.sistemaxm.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="calendario_licenciamento")
public class CalendarioLicenciamento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private char finalPlaca;
	private int mesVencimento;
	private int diaVencimento;

	public CalendarioLicenciamento() {
		
	}
	
	public CalendarioLicenciamento(Long codigo, char finalPlaca, int mesVencimento, int diaVencimento) {
		this.codigo = codigo;
		this.finalPlaca = finalPlaca;
		this.mesVencimento = mesVencimento;
		this.diaVencimento = diaVencimento;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public char getFinalPlaca() {
		return finalPlaca;
	}

	public void setFinalPlaca(char finalPlaca) {
		this.finalPlaca = finalPlaca;
	}

	public int getMesVencimento() {
		return mesVencimento;
	}

	public void setMesVencimento(int mesVencimento) {
		this.mesVencimento = mesVencimento;
	}

	public int getDiaVencimento() {
		return diaVencimento;
	}

	public void setDiaVencimento(int diaVencimento) {
		this.diaVencimento = diaVencimento;
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
		CalendarioLicenciamento other = (CalendarioLicenciamento) obj;
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
