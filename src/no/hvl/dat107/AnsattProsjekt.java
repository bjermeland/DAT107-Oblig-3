package no.hvl.dat107;

import javax.persistence.*;

@Entity
@Table(name = "AnsattProsjekt", schema = "oblig3")
@IdClass(AnsattProsjektPK.class)
public class AnsattProsjekt {
	
	@Id
	private int ansId;
	@Id
	private int proId;

	private int timer;
	private String rolle;
	
	
	public AnsattProsjekt() {
		
	}
	
	public AnsattProsjekt(int ansId, int proId, int timer, String rolle) {
		this.ansId = ansId;
		this.proId = proId;
		this.timer = timer;
		this.rolle = rolle;
	}
	
	public int getAnsId() {
		return ansId;
	}
	
	public int getProId() {
		return proId;
	}
	
	public int getTimer() {
		return this.timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public String getRolle() {
		return this.rolle;
	}

	public void setRolle(String rolle) {
		this.rolle = rolle;
	}

}
