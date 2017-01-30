package br.com.sistemaxm.entidades;

import java.beans.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import br.com.sistemaxm.enums.TipoRota;

@Entity
@Table(name = "jornada_transporte")
public class JornadaTransporte implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long codigo;
	private Carro carro;
	private Funcionario funcionario;
	private Cliente cliente;
	private Abastecimento abastecimento;
	private Date dataJornada;
	private Date inicioJornada;
	private Date fimJornada;	
	private Date intervalo;
	private Date jornadaTotal;
	private Long kmInicial;
	private Long kmFinal;
	private Long kmTotalPercorrido;
	private Long kmExcedente;
	private String extra;
	private Double custoHoraExtra;
	private String obs;
	private TipoRota tipoRota;
	private String inicioJornadaTransient;
	private String fimJornadaTransient;	
	private String intervaloTransient;
	

	public JornadaTransporte() {
		super();
	}
	
	public JornadaTransporte(Long codigo, Carro carro, Funcionario funcionario, Cliente cliente) {
		this.codigo = codigo;
		this.carro = carro;
		this.funcionario = funcionario;
		this.cliente = cliente;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@OneToOne
	@JoinColumn(name = "cod_carro")
	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public Date getInicioJornada() {
		return inicioJornada;
	}

	public void setInicioJornada(Date inicioJornada) {
		this.inicioJornada = inicioJornada;
	}

	public Date getFimJornada() {
		return fimJornada;
	}

	public void setFimJornada(Date fimJornada) {
		this.fimJornada = fimJornada;
	}

	public Date getJornadaTotal() {
		return jornadaTotal;
	}

	public void setJornadaTotal(Date jornadaTotal) {
		this.jornadaTotal = jornadaTotal;
	}
	
	public Date getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Date intervalo) {
		this.intervalo = intervalo;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	public Double getCustoHoraExtra() {
		return custoHoraExtra;
	}

	public void setCustoHoraExtra(Double custoHoraExtra) {
		this.custoHoraExtra = custoHoraExtra;
	}

	@Enumerated(EnumType.STRING)
	public TipoRota getTipoRota() {
		return tipoRota;
	}

	public void setTipoRota(TipoRota tipoRota) {
		this.tipoRota = tipoRota;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
	@OneToOne
	@JoinColumn(name="cod_funcionario")
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDataJornada() {
		return dataJornada;
	}

	public void setDataJornada(Date dataJornada) {
		this.dataJornada = dataJornada;
	}
	@OneToOne
	@JoinColumn(name="cod_cliente")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getKmInicial() {
		return kmInicial;
	}

	public void setKmInicial(Long kmInicial) {
		this.kmInicial = kmInicial;
	}

	public Long getKmFinal() {
		return kmFinal;
	}

	public void setKmFinal(Long kmFinal) {
		this.kmFinal = kmFinal;
	}

	public Long getKmTotalPercorrido() {
		return kmTotalPercorrido;
	}

	public void setKmTotalPercorrido(Long kmTotalPercorrido) {
		this.kmTotalPercorrido = kmTotalPercorrido;
	}

	public Long getKmExcedente() {
		return kmExcedente;
	}

	public void setKmExcedente(Long kmExcedente) {
		this.kmExcedente = kmExcedente;
	}
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="cod_abastecimento")
	public Abastecimento getAbastecimento() {
		return abastecimento;
	}

	public void setAbastecimento(Abastecimento abastecimento) {
		this.abastecimento = abastecimento;
	}
	

	@Transient	
	public String getInicioJornadaTransient() {
		return inicioJornadaTransient;
	}

	public void setInicioJornadaTransient(String inicioJornadaTransient) {
		this.inicioJornadaTransient = inicioJornadaTransient;
	}
	@Transient
	public String getFimJornadaTransient() {
		return fimJornadaTransient;
	}

	public void setFimJornadaTransient(String fimJornadaTransient) {
		this.fimJornadaTransient = fimJornadaTransient;
	}
	@Transient
	public String getIntervaloTransient() {
		return intervaloTransient;
	}

	public void setIntervaloTransient(String intervaloTransient) {
		this.intervaloTransient = intervaloTransient;
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
		JornadaTransporte other = (JornadaTransporte) obj;
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
