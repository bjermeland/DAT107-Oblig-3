package no.hvl.dat107.dao;

import java.util.List;

import javax.persistence.*;

import no.hvl.dat107.Ansatt;
import no.hvl.dat107.AnsattProsjekt;
import no.hvl.dat107.AnsattProsjektPK;
import no.hvl.dat107.Prosjekt;

public class AnsattProsjektDAO {

	private EntityManagerFactory emf;

	public AnsattProsjektDAO() {
		emf = Persistence.createEntityManagerFactory("oblig3PersistenceUnit");
	}

	public List<AnsattProsjekt> hentAnsatteForProsjekt(Prosjekt p) {

		// query string med parameter
		String jpql = "SELECT p FROM AnsattProsjekt p WHERE p.proId = :idp ORDER BY p.ansId";

		EntityManager em = emf.createEntityManager();

		List<AnsattProsjekt> ansatte = null;

		try {
			ansatte = em.createQuery(jpql, AnsattProsjekt.class).setParameter("idp", p.getId()).getResultList();

			if (ansatte.isEmpty())
				return null;

		} finally {
			em.close();
		}

		return ansatte;
	}

	public int hentTotalTimer(Prosjekt p) {
		List<AnsattProsjekt> ansatte = hentAnsatteForProsjekt(p);
		if (ansatte == null) {
			return 0;
		}
		int totalTimer = 0;
		for (AnsattProsjekt ap : ansatte) {
			totalTimer += ap.getTimer();
		}

		return totalTimer;
	}

	public void leggTilAnsattProsjekt(Ansatt a, Prosjekt p, String r) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			AnsattProsjekt ap = new AnsattProsjekt(a.getId(), p.getId(), 0, r);
			em.persist(ap);

			tx.commit();
		} catch (Throwable s) {
			if (tx.isActive())
				tx.rollback();
		} finally {
			em.close();
		}
	}

	public List<AnsattProsjekt> hentProsjektForAnsatt(Ansatt a) {
		String q = "SELECT p FROM AnsattProsjekt p WHERE p.ansId = :aid";
		EntityManager em = emf.createEntityManager();
		List<AnsattProsjekt> prosjekter = null;

		try {
			prosjekter = em.createQuery(q, AnsattProsjekt.class).setParameter("aid", a.getId()).getResultList();
		} catch (Exception e) {
			System.out.println("Feilmelding " + e.getMessage());
		} finally {
			em.close();
		}

		return prosjekter;
	}

	public AnsattProsjekt hentAnsattProsjekt(Ansatt a, Prosjekt p) {
		String q = "SELECT p FROM AnsattProsjekt p WHERE p.ansId = :aid AND p.proId = :pid";
		EntityManager em = emf.createEntityManager();
		AnsattProsjekt ap = null;

		try {
			ap = em.createQuery(q, AnsattProsjekt.class).setParameter("aid", a.getId()).setParameter("pid", p.getId())
					.getSingleResult();
		} catch (Exception e) {
			System.out.println("Feilmelding " + e.getMessage());
		} finally {
			em.close();
		}

		return ap;
	}

	public void oppdaterTimer(AnsattProsjekt ap, int timer) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			int eksisterendeTimer = ap.getTimer();
			ap.setTimer(eksisterendeTimer + timer);
			em.merge(ap);
			tx.commit();
		} catch (Throwable s) {
			if (tx.isActive())
				tx.rollback();
		} finally {
			em.close();
		}
	}

	public void fjernAnsattFraProsjekt(AnsattProsjekt p) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.merge(p));
			tx.commit();
		} catch (Throwable s) {
			if (tx.isActive())
				tx.rollback();
		} finally {
			em.close();
		}
	}

}
