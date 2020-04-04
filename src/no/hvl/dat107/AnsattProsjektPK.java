package no.hvl.dat107;

public class AnsattProsjektPK {
	
	private int ansId;
	private int proId;
	
	public AnsattProsjektPK() {
		
	}
	
	public AnsattProsjektPK(int proId) {
		this.proId = proId;
	}
	
	public AnsattProsjektPK(int ansId, int proId) {
		this.ansId = ansId;
		this.proId = proId;
	}

}
