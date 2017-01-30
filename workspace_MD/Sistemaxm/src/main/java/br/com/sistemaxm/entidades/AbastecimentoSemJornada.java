package br.com.sistemaxm.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="abastecimento_sem_jornada")
public class AbastecimentoSemJornada extends Abastecimento {

	private static final long serialVersionUID = 1L;
	private Carro carro;
	private Date data;
	private Long kmAnterior;
	private Long kmPercorrido;
	
	
	public AbastecimentoSemJornada(Carro carro) {
		this.carro = carro;
	}
	
	public AbastecimentoSemJornada() {
		
	}
	
	@OneToOne
	@JoinColumn(name="cod_carro")
	public Carro getCarro() {
		return carro;
	}
	public void setCarro(Carro carro) {
		this.carro = carro;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Long getKmAnterior() {
		return kmAnterior;
	}

	public void setKmAnterior(Long kmAnterior) {
		this.kmAnterior = kmAnterior;
	}

	public Long getKmPercorrido() {
		return kmPercorrido;
	}

	public void setKmPercorrido(Long kmPercorrido) {
		this.kmPercorrido = kmPercorrido;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((carro == null) ? 0 : carro.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbastecimentoSemJornada other = (AbastecimentoSemJornada) obj;
		if (carro == null) {
			if (other.carro != null)
				return false;
		} else if (!carro.equals(other.carro))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AbastecimentoSemJornada [carro=" + carro + "]";
	}

	
}
