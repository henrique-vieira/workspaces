package br.com.sistemaxm.entidades;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Entity implementation class for Entity: ItemPeca
 *
 */
@XmlRootElement
@Entity
@Table(name="item_peca", uniqueConstraints={@UniqueConstraint(columnNames=("descricao"))})
public class ItemPeca implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private String descricao;
	private String complemento;
	private Long qtde;	
	private Long qtdeEntrada;
	private Double valor;
	private Double valorUnitario;
	private Date dataEntrada;
	private Fornecedor fornecedor;
	private List<Manutencao> listaManutencoes;

	public ItemPeca() {
		super();
	}   
	
	public ItemPeca(Long codigo, String descricao) {
		this.descricao = descricao;
		this.codigo = codigo;
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
	public Long getQtde() {
		return qtde;
	}
	public void setQtde(Long qtde) {
		this.qtde = qtde;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	@ManyToOne
	@JoinColumn(name="cod_fornecedor", referencedColumnName="codigo")
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
		
	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	public Long getQtdeEntrada() {
		return qtdeEntrada;
	}

	public void setQtdeEntrada(Long qtdeEntrada) {
		this.qtdeEntrada = qtdeEntrada;
	}

	@OneToMany(mappedBy="itemPeca",fetch=FetchType.EAGER)	
	public List<Manutencao> getListaManutencoes() {
		return listaManutencoes;
	}

	public void setListaManutencoes(List<Manutencao> listaManutencoes) {
		this.listaManutencoes = listaManutencoes;
	}
	
	

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
		ItemPeca other = (ItemPeca) obj;
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
