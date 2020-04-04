package no.hvl.dat107.dao;

import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import no.hvl.dat107.Ansatt;
import no.hvl.dat107.Avdeling;

public class AvdelingDAO {
	
	private EntityManagerFactory emf;

	public AvdelingDAO() {
		emf = Persistence.createEntityManagerFactory("oblig3PersistenceUnit");
	}
	
	public Avdeling finnAvdelingMedId(int id) {
		EntityManager em = emf.createEntityManager();
		Avdeling a = null;

		try {
			a = em.find(Avdeling.class, id);
		} finally {
			em.close();
		}

		return a;
	}
	
	public List<Avdeling> listAvdelinger() {
		String q = "SELECT a FROM Avdeling a ORDER BY a.avdId";
		EntityManager em = emf.createEntityManager();
		List<Avdeling> avd = null;
		
		try {
			avd = em.createQuery(q, Avdeling.class).getResultList();
		} catch (Exception e) {
			System.out.println("Feilmelding " + e.getMessage());
		} finally {
			em.close();
		}

		return avd;
	}
	
	public void oppdaterAvdelingTilAnsatt(Ansatt a, Avdeling avdeling) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
	
		try {
			tx.begin();
			a.setAvdeling(avdeling);
			em.merge(a);
			tx.commit();
		} catch (Throwable s) {
			if(tx.isActive()) tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public void fjernAvdeling(Avdeling avdeling) {		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.merge(avdeling));
			tx.commit();
		} catch (Throwable s) {
			if (tx.isActive())
				tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public void leggTilNyAvdeling(Avdeling ny) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			em.persist(ny);
			tx.commit();
		} catch (Throwable s) {
			if(tx.isActive()) tx.rollback();
		} finally {
			em.close();
		}
	}
}
		

