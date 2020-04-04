package no.hvl.dat107;

import javax.persistence.*;

@Entity
@Table(name = "Prosjekt", schema = "oblig3")
public class Prosjekt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int proId;
	
	private String navn;
	private String beskrivelse;
	
	public Prosjekt() {
		
	}
	
	public int getId() {
		return proId;
	}
	
	public String getNavn() {
		return navn;
	}
	
	public void setNavn(String navn) {
		this.navn = navn;
	}
	
	public String getBeskrivelse() {
		return beskrivelse;
	}
	
	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}
	
	@Override
	public String toString() {
		return "Prosjekt [ ID: " + this.proId + " | Navn: " + this.navn + " | Beskrivelse: " + this.beskrivelse + " ]";
	}
}
