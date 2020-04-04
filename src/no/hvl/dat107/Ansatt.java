package no.hvl.dat107;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "Ansatt", schema = "oblig3")
public class Ansatt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ansId;
	
	private String brukernavn;
	private String fornavn;
	private String etternavn;
	private LocalDate ansettelsesdato;
	private String stilling;
	private int maanedslonn;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "avdId")
	private Avdeling avdeling;

	// need to have this default constructor, else eclipse persistence error
	public Ansatt() {
		
	}
	
	public Ansatt(int id, String brukernavn, String fornavn, String etternavn, LocalDate ansettelsesdato,
			String stilling, int maanedslonn) {
		this.ansId = id;
		this.brukernavn = brukernavn;
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.ansettelsesdato = ansettelsesdato;
		this.stilling = stilling;
		this.maanedslonn = maanedslonn;
	}
	
	public int getId() {
		return ansId;
	}

	public int getMaanedslonn() {
		return maanedslonn;
	}

	public void setMaanedslonn(int maanedslonn) {
		this.maanedslonn = maanedslonn;
	}

	public String getStilling() {
		return stilling;
	}

	public void setStilling(String stilling) {
		this.stilling = stilling;
	}

	public LocalDate getAnsettelsesDato() {
		return ansettelsesdato;
	}

	public void setAnsettelsesDato(LocalDate ansettelsesdato) {
		this.ansettelsesdato = ansettelsesdato;
	}

	public String getEtternavn() {
		return etternavn;
	}

	public void setEtternavn(String etternavn) {
		this.etternavn = etternavn;
	}

	public String getFornavn() {
		return fornavn;
	}

	public void setFornavn(String fornavn) {
		this.fornavn = fornavn;
	}

	public String getBrukernavn() {
		return brukernavn;
	}

	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
	}
	
	public void setAvdeling(Avdeling avdeling) {
		this.avdeling = avdeling;
	}
	
	public Avdeling getAvdeling() {
		return avdeling;
	}

	@Override
	public String toString() {
		return "Ansatt [ ID: " + this.ansId + " | Brukernavn: " + this.brukernavn +
				" | Navn: " + this.fornavn + " " + this.etternavn + 
				" | Stilling: " + this.stilling + " | Månedslønn: " + this.maanedslonn + " | Avdeling: " + this.avdeling.getNavn() + " ]";
	}
}
