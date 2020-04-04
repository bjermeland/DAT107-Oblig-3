package no.hvl.dat107.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import no.hvl.dat107.Ansatt;
import no.hvl.dat107.Prosjekt;

public class ProsjektDAO {
	
	private EntityManagerFactory emf;
	
	public ProsjektDAO() {
		emf = Persistence.createEntityManagerFactory("oblig3PersistenceUnit");
	}
	
	public Prosjekt finnProsjektMedId(int id) {
		EntityManager em = emf.createEntityManager();
		Prosjekt p = null;

		try {
			p = em.find(Prosjekt.class, id);
		} finally {
			em.close();
		}

		return p;
	}
	
	public List<Prosjekt> listProsjekter() {
		String q = "SELECT p FROM Prosjekt p ORDER BY p.proId";
		EntityManager em = emf.createEntityManager();
		List<Prosjekt> prosjekter = null;
		
		try {
			prosjekter = em.createQuery(q, Prosjekt.class).getResultList();
		} catch (Exception e) {
			System.out.println("Feilmelding " + e.getMessage());
		} finally {
			em.close();
		}

		return prosjekter;
	}
	
	public void leggtilProsjekt(Prosjekt p) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			em.persist(p);
			tx.commit();
		} catch (Throwable s) {
			if(tx.isActive()) tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public void fjernProsjekt(Prosjekt p) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Prosjekt _prosjekt = em.find(Prosjekt.class, p.getId());
		
		try {
			tx.begin();
			em.remove(_prosjekt);
			tx.commit();
		} catch (Throwable s) {
			if(tx.isActive()) tx.rollback();
		} finally {
			em.close();
		}
	}
	
	
	
	
}
