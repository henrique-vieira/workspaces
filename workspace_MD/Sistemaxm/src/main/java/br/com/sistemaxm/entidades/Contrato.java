package br.com.sistemaxm.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Contrato implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private Date dataContrato;
	private Cliente cliente;
	private Carro carro;
	private String local;
	private String capacidade;
	private String prazo;
	private Double valorMensal;
	private Double viagemExtra;
	private Double kmExcedente;
	private Double adicionalNoturno;

	public Contrato(){
		
	}
	public Contrato(Long codigo) {
		this.codigo = codigo;
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDataContrato() {
		return dataContrato;
	}
	public void setDataContrato(Date dataContrato) {
		this.dataContrato = dataContrato;
	}
	
	@OneToOne
	@JoinColumn(name="cod_cliente")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@OneToOne
	@JoinColumn(name="cod_carro")
	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(String capacidade) {
		this.capacidade = capacidade;
	}

	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	public Double getValorMensal() {
		return valorMensal;
	}

	public void setValorMensal(Double valorMensal) {
		this.valorMensal = valorMensal;
	}

	public Double getViagemExtra() {
		return viagemExtra;
	}

	public void setViagemExtra(Double viagemExtra) {
		this.viagemExtra = viagemExtra;
	}

	public Double getKmExcedente() {
		return kmExcedente;
	}

	public void setKmExcedente(Double kmExcedente) {
		this.kmExcedente = kmExcedente;
	}
		
	public Double getAdicionalNoturno() {
		return adicionalNoturno;
	}
	public void setAdicionalNoturno(Double adicionalNoturno) {
		this.adicionalNoturno = adicionalNoturno;
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
		Contrato other = (Contrato) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
		
}
