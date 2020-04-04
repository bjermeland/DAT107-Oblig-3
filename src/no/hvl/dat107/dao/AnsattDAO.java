package no.hvl.dat107.dao;

import javax.persistence.*;
import java.util.List;

import no.hvl.dat107.Ansatt;
import no.hvl.dat107.Avdeling;

public class AnsattDAO {

	private EntityManagerFactory emf;

	public AnsattDAO() {
		emf = Persistence.createEntityManagerFactory("oblig3PersistenceUnit");
	}

	/*
	 * Henter ut ansatt ved bruk av ID
	 * 
	 * @param id som skal søkes etter
	 * 
	 * @return referanse til ansatt, null ellers
	 */
	public Ansatt finnAnsattMedId(int id) {
		EntityManager em = emf.createEntityManager();
		Ansatt a = null;

		try {
			a = em.find(Ansatt.class, id);
		} finally {
			em.close();
		}

		return a;
	}
	
	/*
	 * Henter ut ansatt ved brukernavn
	 * 
	 * @param brukernavn som skal søkes etter
	 * 
	 * @return referanse til ansatt, null ellers
	 */
	public Ansatt finnAnsattMedBrukernavn(String brukernavn) {
		// query string med parameter
		String jpql = "SELECT a FROM Ansatt a WHERE a.brukernavn = :Name";

		EntityManager em = emf.createEntityManager();

		try {
			List<Ansatt> ansatte = em.createQuery(jpql, Ansatt.class).setParameter("Name", brukernavn).getResultList();

			if (ansatte.isEmpty())
				return null;
			else if (ansatte.size() == 1)
				return ansatte.get(0);
		} finally {
			em.close();
		}

		return null;
	}

	public List<Ansatt> listAnsatte() {
		String q = "SELECT a FROM Ansatt a ORDER BY a.ansId";
		EntityManager em = emf.createEntityManager();
		List<Ansatt> ansatte = null;
		
		try {
			ansatte = em.createQuery(q, Ansatt.class).getResultList();
		} catch (Exception e) {
			System.out.println("Feilmelding " + e.getMessage());
		} finally {
			em.close();
		}

		return ansatte;
	}

	public void oppdaterAnsattMedStilling(Ansatt a, String stilling) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			a.setStilling(stilling);
			em.merge(a);
			tx.commit();
		} catch (Throwable s) {
			if(tx.isActive()) tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public void oppdaterAnsattMedAvdeling(Ansatt a, Avdeling b) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			a.setAvdeling(b);
			em.merge(a);
			tx.commit();
		} catch (Throwable s) {
			if(tx.isActive()) tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public void leggTilNyAnsatt(Ansatt ny) {
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
	
	public void fjernAnsatt(Ansatt a) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Ansatt _ansatt = em.find(Ansatt.class, a.getId());
		_ansatt.setAvdeling(null);
		
		try {
			tx.begin();
			em.remove(_ansatt);
			tx.commit();
		} catch (Throwable s) {
			if(tx.isActive()) tx.rollback();
		} finally {
			em.close();
		}
	}
}
