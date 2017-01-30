package com.vibratecnologia.cobranca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vibratecnologia.cobranca.model.Titulo;

public interface Titulos extends JpaRepository <Titulo, Long>{

	public List<Titulo> findByDescricaoContaining(String descricao);/*realiza a busca para a caixa de pesquisa, 
																	  //encontrando qualquer expressão contida na descricao*/
																	// "Containing" equivale ao "like" do sql 
}
