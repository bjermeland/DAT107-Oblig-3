package no.hvl.dat107;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import no.hvl.dat107.dao.*;

public class Grensesnitt {

	private AnsattDAO ansattDAO;
	private AvdelingDAO avdelingDAO;
	private ProsjektDAO prosjektDAO;
	private AnsattProsjektDAO ansattprosjektDAO;

	public Grensesnitt() {
		this.ansattDAO = new AnsattDAO();
		this.avdelingDAO = new AvdelingDAO();
		this.prosjektDAO = new ProsjektDAO();
		this.ansattprosjektDAO = new AnsattProsjektDAO();
	}

	public void start() {

		boolean done = false;
		while (!done) {

			kategorier();

			String s = getInput();
			switch (s) {
			case "1":
				System.out.println("\nKATEGORI: Ansatt");
				System.out.println("\n1. Søk etter ansatt med ID");
				System.out.println("\n2. Søk etter ansatt med BRUKERNAVN");
				System.out.println("\n3. List alle ansatte");
				System.out.println("\n4. Oppdater stilling til ansatt");
				System.out.println("\n5. Legg til ny ansatt");
				System.out.println("\n6. Fjern ansatt");
				String valg = getInput();

				ansattKategori(valg);

				break;

			case "2":
				System.out.println("\nKATEGORI: Avdeling");
				System.out.println("\n1. Søk etter avdeling med ID");
				System.out.println("\n2. List alle ansatte i avdeling");
				System.out.println("\n3. Oppdater avdeling til ansatt");
				System.out.println("\n4. Legg til ny avdeling");
				System.out.println("\n5. Fjern en avdeling");
				String valgTo = getInput();

				avdelingKategori(valgTo);

				break;
				
			case "3":
				System.out.println("\nKATEGORI: Prosjekt");
				System.out.println("\n1. Legg til et nytt prosjekt");
				System.out.println("\n2. List alle prosjekter");
				System.out.println("\n3. List detaljert info om prosjekt");
				System.out.println("\n4. Fjern prosjekt");
				System.out.println("\n5. Legg til ansatt på prosjekt");
				System.out.println("\n6. Legg til timer for ansatt");
				System.out.println("\n7. Fjern ansatt fra prosjekt");
				String valgTre = getInput();

				prosjektKategori(valgTre);
				
				break;

			default:
				System.out.println("\nUgyldig valg, skriv noe for å prøv igjen.");
				s = getInput();
			}
		}
	}

	public void kategorier() {
		System.out.println("\nKATEGORIER");
		System.out.println("\n1. Ansatt");
		System.out.println("\n2. Avdeling");
		System.out.println("\n3. Prosjekt");
	}

	public void ansattKategori(String valg) {
		switch (valg) {
		case "1":
			System.out.println("\nID: ");
			int id = 0;
			try {
				id = Integer.parseInt(getInput());
			} catch (Exception ex) {
				System.out.println("\nInntasting ugyldig. Prøv igjen");
				ansattKategori(valg);
			}

			Ansatt a = ansattDAO.finnAnsattMedId(id);
			if (a != null)
				System.out.println("\n" + a);
			else
				System.out.println("\nKunne ikke finne ansatt med ID = { " + id + " }");

			break;

		case "2":
			System.out.println("\nBRUKERNAVN: ");
			String br = "";
			try {
				br = getInput();
			} catch (Exception ex) {
				System.out.println("\nInntasting ugyldig. Prøv igjen");
				ansattKategori(valg);
			}

			Ansatt b = ansattDAO.finnAnsattMedBrukernavn(br);
			if (b != null)
				System.out.println("\n" + b);
			else
				System.out.println("\nKunne ikke finne ansatt med BRUKERNAVN = { " + br + " }");

			break;

		case "3":
			System.out.println("\nLister alle ansatte:\n");
			List<Ansatt> ansatte = ansattDAO.listAnsatte();
			for (Ansatt an : ansatte) {
				System.out.println(an);
			}
			break;

		case "4":
			System.out.println("\nOppdater ansatt med BRUKERNAVN = \n");
			String bru = "";
			try {
				bru = getInput();
			} catch (Exception ex) {
				System.out.println("\nInntasting ugyldig. Prøv igjen");
				ansattKategori(valg);
			}

			Ansatt an = ansattDAO.finnAnsattMedBrukernavn(bru);
			if (an != null)
				System.out.println(an.toString());
			else
				System.out.println("\nKunne ikke finne ansatt med BRUKERNAVN = {1 " + bru + " }");

			System.out.println("Nåværende stilling er = { " + an.getStilling() + " }");
			System.out.println("\nTast inn ny stilling = ");

			String nyStilling = getInput();

			ansattDAO.oppdaterAnsattMedStilling(an, nyStilling);

			System.out.println(an);

			break;

		case "5":
			System.out.println("Ny ansatt");
			Ansatt ans = new Ansatt();
			System.out.println("\nBrukernavn: ");
			String brukernavn = getInput();
			ans.setBrukernavn(brukernavn);
			System.out.println("\nFornavn: ");
			ans.setFornavn(getInput());
			System.out.println("\nEtternavn: ");
			ans.setEtternavn(getInput());

			ans.setAnsettelsesDato(LocalDate.now());

			System.out.println("\nStilling: ");
			ans.setStilling(getInput());

			System.out.println("\nMånedslønn: ");
			ans.setMaanedslonn(Integer.parseInt(getInput()));
			
			System.out.println("\nTilgjengelige avdelinger: ");
			List<Avdeling> avd2 = avdelingDAO.listAvdelinger();
			for (Avdeling av : avd2) {
				System.out.println("\n" + av);
			}
			System.out.println("\nAvdeling (tast inn ID): ");
			Avdeling avdd = avdelingDAO.finnAvdelingMedId(Integer.parseInt(getInput()));

			ansattDAO.leggTilNyAnsatt(ans);
			
			// må oppdatere avdeling på ansatt etter ansatt er laget for å unngå "kolonne"-error
			Ansatt nyAnsatt = ansattDAO.finnAnsattMedBrukernavn(brukernavn);
			ansattDAO.oppdaterAnsattMedAvdeling(nyAnsatt, avdd);
			
			System.out.print("\nAnsatt lagt til vellykket.\n");

			break;
			
		case "6":
			System.out.println("\nFjern ansatt\n");
			
			List<Ansatt> fjern_list = ansattDAO.listAnsatte();
			for (Ansatt fjern_ansatt : fjern_list) {
				System.out.println(fjern_ansatt);
			}
			
			System.out.println("\nVelg ansatt du vil fjerne (med ID): ");
			
			Ansatt ans_fjern = ansattDAO.finnAnsattMedId(Integer.parseInt(getInput()));
			
			List<Avdeling> list_avd = avdelingDAO.listAvdelinger();
			boolean funnet = false;
			for(Avdeling avd : list_avd) {
				if(avd.getSjef().getId() == ans_fjern.getId()) {
					funnet = true;
				}
			}
			
			if(funnet) {
				System.out.println("\nKan ikke fjerne ansatt som er sjef.");
				break;
			}
			
			ansattDAO.fjernAnsatt(ans_fjern);
			
			System.out.println("\nAnsatt fjernet vellykket.");
			
			break;

		default:
			System.out.println("\nUgyldig valg, prøv igjen.");
			String nyValg = getInput();
			ansattKategori(nyValg);
		}
	}

	public void avdelingKategori(String valg) {
		switch (valg) {
		case "1":
			System.out.println("\nID: ");
			int id = 0;
			try {
				id = Integer.parseInt(getInput());
			} catch (Exception ex) {
				System.out.println("\nInntasting ugyldig. Prøv igjen");
				avdelingKategori(valg);
			}

			Avdeling a = avdelingDAO.finnAvdelingMedId(id);
			if (a != null)
				System.out.println(a);
			else
				System.out.println("\nKunne ikke finne avdeling med ID = { " + id + " }");

			break;

		case "2":
			System.out.println("\nAvdelinger:\n");
			List<Avdeling> avd = avdelingDAO.listAvdelinger();

			for (Avdeling av : avd) {
				System.out.println(av);
			}
			
			System.out.println("\nVelg avdeling du vil se ansatte (ID): \n");
			
			int input = Integer.parseInt(getInput());
			List<Ansatt> ansatte = ansattDAO.listAnsatte();
			Avdeling avdNy = avdelingDAO.finnAvdelingMedId(input);
			
			for (Ansatt an : ansatte) {
				if(avdNy.getId() == an.getAvdeling().getId()) { 
					
					int sjef_id = avdNy.getSjef().getId();
					if(an.getId() == sjef_id) {
						System.out.println("\nSJEF | " + an);
					} else {
						System.out.println("\n" + an);
					}
				}
			}
			
			break;

		case "3":
			System.out.println("\nOppdater avdeling til ansatt med BRUKERNAVN = \n");
			String bru = "";
			
			try {
				bru = getInput();
			} catch (Exception ex) {
				System.out.println("\nInntasting ugyldig. Prøv igjen");
				ansattKategori(valg);
			}

			Ansatt an = ansattDAO.finnAnsattMedBrukernavn(bru);
			
			// sjekk om ansatt er sjef, om han er sjef kan han ikke bytte avdeling
			List<Avdeling> l_avd = avdelingDAO.listAvdelinger();
			List<Ansatt> l_ans = ansattDAO.listAnsatte();
			boolean funnet = false;
			for(Avdeling f : l_avd) {
				if(f.getSjef().getId() == an.getId()) {
					funnet = true;
				}
			}
			if(funnet) {
				System.out.println("\nDu kan ikke oppdatere avdelingen til en sjef.\n");
				break;
			}
			
			if (an != null)
				System.out.println(an.toString());
			else
				System.out.println("\nKunne ikke finne ansatt med BRUKERNAVN = { " + bru + " }");

			System.out.println("\nNåværende avdeling er = { " + an.getAvdeling().getNavn() + " }");
			
			System.out.println("\nTilgjengelige avdelinger: \n");
			List<Avdeling> avd2 = avdelingDAO.listAvdelinger();
			for (Avdeling av : avd2) {
				System.out.println(av);
			}
			System.out.println("\nTast inn ny avdeling (med ID): ");
			
	
			Avdeling avv = avdelingDAO.finnAvdelingMedId(Integer.parseInt(getInput()));
			
			avdelingDAO.oppdaterAvdelingTilAnsatt(an, avv);
			
			System.out.println("\nOppdatert avdeling vellykket.");

			break;

		case "4":
			System.out.println("\nNy avdeling");
			Avdeling avdel = new Avdeling();
			System.out.println("\nNavn: ");
			avdel.setNavn(getInput());

			List<Ansatt> ansatter = ansattDAO.listAnsatte();
			List<Avdeling> avd_c4 = avdelingDAO.listAvdelinger();

			// komplisert metode, samler først alle sjefer i en liste, sammenligner så alle ansatte med sjefer og fjerner sjefer fra ansatte listen.
			System.out.println("\nDu må velge en sjef, her er alle tilgjengelige: \n");
			List<Ansatt> sjefer = new ArrayList<>();
			for(Avdeling f : avd_c4) {
				for(Ansatt g : ansatter) {
					if(f.getSjef().getId() == g.getId()) {
						sjefer.add(g);
					}
				}
			}
			ansatter.removeAll(sjefer);
			for(Ansatt h : ansatter) {
				System.out.println(h);
			}

			
			System.out.println("\nTast inn ny sjef (med ID): ");
			
			Ansatt sjef = ansattDAO.finnAnsattMedId(Integer.parseInt(getInput()));
			
			avdel.setSjef(sjef);
			avdelingDAO.leggTilNyAvdeling(avdel);
			
			// må bytte avdeling til valgt sjef
			ansattDAO.oppdaterAnsattMedAvdeling(sjef, avdel);
			
			System.out.print("\nAvdeling lagt til vellykket.\n");

			break;
			
		case "5":
			System.out.println("\nAvdelinger: \n");
			List<Avdeling> list_avd = avdelingDAO.listAvdelinger();
			for (Avdeling av : list_avd) {
				System.out.println(av);
			}
			System.out.println("\nTast inn avdeling å fjerne (med ID): ");
			
			Avdeling new_avd = avdelingDAO.finnAvdelingMedId(Integer.parseInt(getInput()));
			
			if(new_avd.getSjef() != null) {
				System.out.println("\nKan ikke slette en avdeling med ansatte.");
				break;
			}
			
			avdelingDAO.fjernAvdeling(new_avd);
			
			System.out.println("\nAvdeling fjernet vellykket.");
			
			break;

		default:
			System.out.println("\nUgyldig valg, prøv igjen.");
			String nyValg = getInput();
			ansattKategori(nyValg);
		}
	}

	public void prosjektKategori(String valg) {
		switch(valg) {
		case "1":
			System.out.println("\nLegg til nytt prosjekt\n");
			Prosjekt p = new Prosjekt();
			System.out.println("\nNavn: ");
			p.setNavn(getInput());
			
			System.out.println("\nBeskrivelse: ");
			p.setBeskrivelse(getInput());
			
			prosjektDAO.leggtilProsjekt(p);
			
			System.out.println("\nProsjekt lagt til vellykket. Du kan legge til ansatte i menyen.");
			
			break;
			
		case "2":
			List<Prosjekt> list_p = prosjektDAO.listProsjekter();
			System.out.println("\nLister alle prosjekt: \n");
			for(Prosjekt _p : list_p) {
				System.out.println(_p);
			}
			
			if(list_p.isEmpty()) {
				System.out.println("Finnes ingen prosjekt.");
				break;
			}
			
			break;
			
		case "3":
			List<Prosjekt> _list_p = prosjektDAO.listProsjekter();
			System.out.println("\nAlle prosjekter: \n");
			for(Prosjekt _p : _list_p) {
				System.out.println(_p);
			}
			
			if(_list_p.isEmpty()) {
				System.out.println("Finnes ingen prosjekt.");
				break;
			}
			
			System.out.println("\nVelg prosjekt å se detaljert info om (med ID): ");
			
			Prosjekt _pro = prosjektDAO.finnProsjektMedId(Integer.parseInt(getInput()));
			
			System.out.println("\n" + _pro);
			System.out.println("\nTotalt timer for prosjekt: " + ansattprosjektDAO.hentTotalTimer(_pro));
			System.out.println("\nAnsatte: \n");
			
			List<AnsattProsjekt> list_a = ansattprosjektDAO.hentAnsatteForProsjekt(_pro);
			if(list_a == null) {
				System.out.println("Det finnes ingen ansatte i prosjektet.");
				break;
			}
			
			for(AnsattProsjekt ap : list_a) {
				System.out.println("Ansatt [ ID: " + ap.getAnsId() + " | Navn: " + ansattDAO.finnAnsattMedId(ap.getAnsId()).getFornavn() + " " + 
						           ansattDAO.finnAnsattMedId(ap.getAnsId()).getEtternavn() + " | Timer: " + ap.getTimer() + " | Rolle: " + ap.getRolle() + " ]");
			}
			
			break;
			
		case "4":
			System.out.println("\nFjern prosjekt\n");
			
			System.out.println("Følgende prosjekt eksisterer: \n");
			
			List<Prosjekt> list_fjern = prosjektDAO.listProsjekter();
			
			if(list_fjern.isEmpty()) {
				System.out.println("Finnes ingen prosjekt.");
				break;
			}
			
			for(Prosjekt p_ : list_fjern) {
				System.out.println(p_);
			}
			
			System.out.println("\nVelg prosjekt du vil fjerne (med ID): ");
			
			Prosjekt _p_fjern = prosjektDAO.finnProsjektMedId(Integer.parseInt(getInput()));
			
			if(ansattprosjektDAO.hentAnsatteForProsjekt(_p_fjern) != null) {
				System.out.println("\nKan ikke fjerne ettersom det er ansatte tilknyttet prosjektet.");
				break;
			}
			
			
			prosjektDAO.fjernProsjekt(_p_fjern);
			
			System.out.println("\nProsjekt fjernet vellykket.");
			
			break;
		
		case "5":
			System.out.println("\nLegg til ansatt på prosjekt\n");
			
			System.out.println("Velg ønsket prosjekt (med ID): \n");
			List<Prosjekt> pro_list = prosjektDAO.listProsjekter();
			
			if(pro_list.isEmpty()) {
				System.out.println("Finnes ingen prosjekt.");
				break;
			}
			
			for(Prosjekt pro : pro_list) {
				System.out.println(pro);
			}
			
			Prosjekt pro = prosjektDAO.finnProsjektMedId(Integer.parseInt(getInput()));
			
			System.out.println("\nVelg ansatt (med ID): \n");
			List<Ansatt> ansatte = ansattDAO.listAnsatte();
			for(Ansatt a : ansatte) {
				System.out.println(a);
			}
			
			Ansatt a = ansattDAO.finnAnsattMedId(Integer.parseInt(getInput()));
			
			System.out.println("\nSkriv inn rolle for ansatt: ");
			
			String rolle_ansatt = getInput(); 
			
			ansattprosjektDAO.leggTilAnsattProsjekt(a, pro, rolle_ansatt);
			
			System.out.println("\nAnsatt lagt til prosjekt vellykket");
			
			break;
			
		case "6":
			System.out.println("\nTimer for ansatt");
			
			System.out.println("\nVelg ansatt (med ID): \n");
			List<Ansatt> list_ansatt = ansattDAO.listAnsatte();
			for(Ansatt a_ : list_ansatt) {
				System.out.println(a_);
			}
			
			Ansatt a_t = ansattDAO.finnAnsattMedId(Integer.parseInt(getInput()));
			
			System.out.println("\nVelg prosjekt (med ID): \n");
			List<AnsattProsjekt> list_prosjekt = ansattprosjektDAO.hentProsjektForAnsatt(a_t);
			
			if(list_prosjekt.isEmpty()) {
				System.out.println("Ansatt er ikke med i noen prosjekt.");
				break;
			}
			
			for(AnsattProsjekt ap : list_prosjekt) {
				System.out.println(prosjektDAO.finnProsjektMedId(ap.getProId()));
			}
			
			Prosjekt p_t = prosjektDAO.finnProsjektMedId(Integer.parseInt(getInput()));
			AnsattProsjekt ap = ansattprosjektDAO.hentAnsattProsjekt(a_t, p_t);
			
			System.out.println("\nNåværende timer for Ansatt { " + a_t.getBrukernavn() + " } i Prosjekt { " + p_t.getNavn() + " } er: " + ap.getTimer() + " timer");
			
			System.out.println("\nSkriv inn antall timer du ønsker å legge til: ");
			
			int nyTimer = Integer.parseInt(getInput());
			ansattprosjektDAO.oppdaterTimer(ap, nyTimer);
			
			System.out.println("\nTimer lagt til.");
			
			break;
			
		case "7":
			System.out.println("\nFjern ansatt fra prosjekt\n");
			
			System.out.println("\nVelg ansatt (med ID): \n");
			List<Ansatt> list = ansattDAO.listAnsatte();
			for(Ansatt a_ : list) {
				System.out.println(a_);
			}
			
			Ansatt a_f = ansattDAO.finnAnsattMedId(Integer.parseInt(getInput()));
			
			System.out.println("\nVelg prosjekt (med ID): \n");
			List<AnsattProsjekt> list_ap = ansattprosjektDAO.hentProsjektForAnsatt(a_f);
			
			if(list_ap.isEmpty()) {
				System.out.println("Ansatt er ikke med i noen prosjekt.");
				break;
			}
			
			for(AnsattProsjekt a_p : list_ap) {
				System.out.println(prosjektDAO.finnProsjektMedId(a_p.getProId()));
			}
			
			Prosjekt p_f = prosjektDAO.finnProsjektMedId(Integer.parseInt(getInput()));
						
			AnsattProsjekt ap_f = ansattprosjektDAO.hentAnsattProsjekt(a_f, p_f);
			
			if(ap_f.getTimer() > 0) {
				System.out.println("\nKan ikke fjerne siden ansatt har registrert timer for prosjektet.");
				break;
			}
			
			ansattprosjektDAO.fjernAnsattFraProsjekt(ap_f);
			
			System.out.println("\nAnsatt fjernet fra prosjekt vellykket.");

		}
	}
	
	public static String getInput() {
		System.out.print("\n> ");
		Scanner input = new Scanner(System.in);
		String svar = "";
		try {
			svar = input.nextLine();
		} catch (Exception ex) {
			System.out.println("\nUgyldig valg.");
		}

		return svar;
	}

}
