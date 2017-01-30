package com.vibratecnologia.util;

import java.io.Serializable;

import org.hibernate.Session;

import com.vibratecnologia.repository.Lancamentos;
import com.vibratecnologia.repository.Pessoas;
import com.vibratecnologia.repository.infra.LancamentosHibernate;
import com.vibratecnologia.repository.infra.PessoasHibernate;

public class Repositorios implements Serializable{
	
	public Pessoas getPessoas(){
		return new PessoasHibernate(this.getSession());
	}
	
	public Lancamentos getLancamentos(){
		return new LancamentosHibernate(this.getSession());
	}
	
	private Session getSession(){
		return (Session) FacesUtil.getRequestAttribute("session");
	}
}
