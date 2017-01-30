package br.com.sistemaxm.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.ToStringBuilder;

import br.com.sistemaxm.enums.StatusFuncionario;

@Entity
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private Date dataAdmissao;
	private Date dataDemissao;
	private String cnh;
	private StatusFuncionario status;
	private Long matricula;
	private Pessoa pessoa;
	private Funcao funcao;	
	private Aso aso;	
	private Cliente cliente;
	private Date vencimentoRac;
	private Date vencimentoCnh;
	private Date dataFerias;
	private Double salario;
	
	public Funcionario() {
		
	}
	
	public Funcionario(Long codigo, Pessoa pessoa) {
		this.codigo = codigo;
		this.pessoa = pessoa;
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}
	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
		
	public Date getDataDemissao() {
		return dataDemissao;
	}

	public void setDataDemissao(Date dataDemissao) {
		this.dataDemissao = dataDemissao;
	}

	public String getCnh() {
		return cnh;
	}
	public void setCnh(String cnh) {
		this.cnh = cnh;
	}
	public Long getMatricula() {
		return matricula;
	}
	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}			

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="cod_pessoa")
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	@ManyToOne
	@JoinColumn(name="cod_funcao")
	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="cod_aso")
	public Aso getAso() {
		return aso;
	}

	public void setAso(Aso aso) {
		this.aso = aso;
	}
	
	public Date getVencimentoRac() {
		return vencimentoRac;
	}

	public void setVencimentoRac(Date vencimentoRac) {
		this.vencimentoRac = vencimentoRac;
	}
	
	public Date getVencimentoCnh() {
		return vencimentoCnh;
	}

	public void setVencimentoCnh(Date vencimentoCnh) {
		this.vencimentoCnh = vencimentoCnh;
	}
	
	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}
	
	@ManyToOne
	@JoinColumn(name="cod_cliente")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	@Enumerated(EnumType.STRING)
	public StatusFuncionario getStatus() {
		return status;
	}

	public void setStatus(StatusFuncionario status) {
		this.status = status;
	}

	
	
	public Date getDataFerias() {
		return dataFerias;
	}

	public void setDataFerias(Date dataFerias) {
		this.dataFerias = dataFerias;
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
		result = prime * result
				+ ((matricula == null) ? 0 : matricula.hashCode());
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
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
		Funcionario other = (Funcionario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		return true;
	}

		

}
