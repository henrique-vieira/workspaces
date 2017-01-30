package br.com.sistemaxm.entidades;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Long;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Manutencao
 *
 */
@Entity

public class Manutencao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private Long numManutencao;
	private ItemPeca itemPeca;
	private Carro carro;
	private Servico servico;
	private Date dataManutencao;
	private Double valor;
	private String obs;
	private Long kmTroca;
	private Long kmProxTroca;
	private int qtde;
	

	public Manutencao() {
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
	
	@ManyToOne
	@JoinColumn(name="cod_item")
	public ItemPeca getItemPeca() {
		return this.itemPeca;
	}

	public void setItemPeca(ItemPeca itemPeca) {
		this.itemPeca = itemPeca;
	}  
	
	@ManyToOne
	@JoinColumn(name="cod_carro")
	public Carro getCarro() {
		return this.carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	} 
	@OneToOne
	@JoinColumn(name="cod_servico")
	public Servico getServico() {
		return this.servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}   
	public Date getDataManutencao() {
		return this.dataManutencao;
	}

	public void setDataManutencao(Date dataManutencao) {
		this.dataManutencao = dataManutencao;
	}   
	public Double getValor() {
		return this.valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	@Column(name="km_troca")
	public Long getKmTroca() {
		return kmTroca;
	}
	public void setKmTroca(Long kmTroca) {
		this.kmTroca = kmTroca;
	} 
	@Column(name="km_prox_troca")
	public Long getKmProxTroca() {
		return kmProxTroca;
	}
	public void setKmProxTroca(Long kmProxTroca) {
		this.kmProxTroca = kmProxTroca;
	}
	public int getQtde() {
		return qtde;
	}
	public void setQtde(int qtde) {
		this.qtde = qtde;
	}
	@Column(name="num_manutencao")
	public Long getNumManutencao() {
		return numManutencao;
	}
	public void setNumManutencao(Long numManutencao) {
		this.numManutencao = numManutencao;
	}
   
	
}
