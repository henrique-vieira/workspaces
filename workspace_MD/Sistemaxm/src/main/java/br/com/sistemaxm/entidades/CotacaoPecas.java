package br.com.sistemaxm.entidades;

import br.com.sistemaxm.entidades.Carro;
import br.com.sistemaxm.entidades.Fornecedor;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;
import javax.persistence.criteria.Join;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Entity implementation class for Entity: CotacaoPecas
 *
 */
@Entity
@Table(name="cotacao_pecas")
public class CotacaoPecas implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private String item;
	private Long qtde;	
	private Double valor;
	private Double valorUnitario;
	private Date dataCotacao;
	private Carro carro;
	private Fornecedor fornecedor;
	

	public CotacaoPecas() {
		super();
	}  
	
	@Id    
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}   
	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}   
	public Long getQtde() {
		return this.qtde;
	}

	public void setQtde(Long qtde) {
		this.qtde = qtde;
	}   
	public Double getValor() {
		return this.valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	} 
	@Temporal(TemporalType.DATE)
	public Date getDataCotacao() {
		return this.dataCotacao;
	}
	
	public void setDataCotacao(Date dataCotacao) {
		this.dataCotacao = dataCotacao;
	}  
	@OneToOne
	@JoinColumn(name="cod_carro")
	public Carro getCarro() {
		return this.carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}   
	
	@OneToOne
	@JoinColumn(name="cod_fornecedor")
	public Fornecedor getFornecedor() {
		return this.fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
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
		CotacaoPecas other = (CotacaoPecas) obj;
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
