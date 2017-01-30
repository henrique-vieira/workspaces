package com.vibratecnologia.repository;

import java.util.List;

import com.vibratecnologia.model.Pessoa;

public interface Pessoas {
	
	public List<Pessoa> todas();
	public Pessoa porCodigo(Integer codigo);
}
