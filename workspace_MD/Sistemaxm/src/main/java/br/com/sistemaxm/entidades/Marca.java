package br.com.sistemaxm.entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.Collection;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;



/**
 * Entity implementation class for Entity: Marca
 *
 */
@Entity
@Table(name ="marca", uniqueConstraints={@UniqueConstraint(columnNames=("descricao"))}) 
public class Marca implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private String descricao;
	
	private Collection<Carro> carros;

	public Marca() {
		super();
	}  
	
		
	public Marca(Long codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
	}
    
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name="codigo")
	public Long getCodigo() {
		return codigo;
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
	
	@OneToMany(mappedBy="marca")
	public Collection<Carro> getCarros() {
		return carros;
	}
	public void setCarros(Collection<Carro> carros) {
		this.carros = carros;
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
		Marca other = (Marca) obj;
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
