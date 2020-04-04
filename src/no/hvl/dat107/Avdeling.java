package no.hvl.dat107;

import javax.persistence.*;

@Entity
@Table(name = "Avdeling", schema = "oblig3")
public class Avdeling {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int avdId;

	private String navn;
	private Ansatt sjef;

	public Avdeling() {

	}

	public Avdeling(int id, String navn, Ansatt sjef) {
		this.avdId = id;
		this.navn = navn;
		this.sjef = sjef;
	}

	public int getId() {
		return avdId;
	}

	public String getNavn() {
		return navn;
	}
	
	public void setNavn(String navn) {
		this.navn = navn;
	}
	
	public void setSjef(Ansatt sjef) {
		this.sjef = sjef;
	}

	public Ansatt getSjef() {
		return sjef;
	}
	
	@Override
	public String toString() {
		return "Avdeling [ ID: " + this.avdId + 
		" | Navn: " + this.navn + 
		" | Sjef: " + this.sjef.getFornavn() + " " + this.sjef.getEtternavn() + " ]";
	}
}
