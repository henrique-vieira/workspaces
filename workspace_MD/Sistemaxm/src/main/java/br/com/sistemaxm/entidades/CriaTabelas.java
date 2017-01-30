package br.com.sistemaxm.entidades;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class CriaTabelas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Persistence.createEntityManagerFactory("Sistemaxm");
	}

}
