package br.com.sistemaxm.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class JpaUtil {

	private static EntityManagerFactory factory;
	
	static {
		factory = Persistence.createEntityManagerFactory("Sistemaxm");
	}
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	public static SessionFactory getSessionFactory() {
		EntityManager em = JpaUtil.getEntityManager();
		Session session = (Session) em.getDelegate();
		SessionFactory sf = session.getSessionFactory();
		
		return sf;
	}
}
