package br.com.sistemaxm.repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class CriaTabelas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Persistence.createEntityManagerFactory("Sistemaxm");
	}

}
