package entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@DiscriminatorValue("TYPE_MISSION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class MissionExterne extends Mission {
	
	private static final long serialVersionUID = -3046278688391172322L;
	
	
	private String emailFacturation;
	
	
	private float tauxJournalierMoyen;

	
	public MissionExterne() {
		super();
	}


	public MissionExterne(String name, String description, String emailFacturation, float tauxJournalierMoyen) {
		super(name, description);
		this.emailFacturation = emailFacturation;
		this.tauxJournalierMoyen = tauxJournalierMoyen;
	}


	public String getEmailFacturation() {
		return emailFacturation;
	}


	public void setEmailFacturation(String emailFacturation) {
		this.emailFacturation = emailFacturation;
	}


	public float getTauxJournalierMoyen() {
		return tauxJournalierMoyen;
	}


	public void setTauxJournalierMoyen(float tauxJournalierMoyen) {
		this.tauxJournalierMoyen = tauxJournalierMoyen;
	}
	
}
