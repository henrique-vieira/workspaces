package br.com.sistemaxm.entidades;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Grupo
 *
 */
@Entity
public class Grupo implements Serializable {

	
	private Long codigoGrupo;
	private String descricao;
	private static final long serialVersionUID = 1L;

	public Grupo() {
		super();
	}   
	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cod_grupo")
	public Long getCodigoGrupo() {
		return this.codigoGrupo;
	}

	public void setCodigoGrupo(Long codigoGrupo) {
		this.codigoGrupo = codigoGrupo;
	}
	@Column(nullable=false)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
   
}
