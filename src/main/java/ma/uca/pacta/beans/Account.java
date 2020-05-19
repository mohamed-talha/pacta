package ma.uca.pacta.beans;

public class Account {
	String id;
	Integer numberOfDirectInfectedContact;
	Integer numberOfIndirectInfectedContact;
	Boolean isInfected;
	Long infectedOn;//TimeStamp
	String infectionDeclaredBy;
	
	public Account() {
		super();
	}
	
	public Account(String id, Integer numberOfDirectInfectedContact, Integer numberOfIndirectInfectedContact, Boolean isInfected, Long infectedOn, String infectionDeclaredBy) {
		super();
		this.id = id;
		this.numberOfDirectInfectedContact = numberOfDirectInfectedContact;
		this.numberOfIndirectInfectedContact = numberOfIndirectInfectedContact;
		this.isInfected = isInfected;
		this.infectedOn = infectedOn;
		this.infectionDeclaredBy = infectionDeclaredBy;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getNumberOfDirectInfectedContact() {
		return numberOfDirectInfectedContact;
	}
	public void setNumberOfDirectInfectedContact(Integer numberOfDirectInfectedContact) {
		this.numberOfDirectInfectedContact = numberOfDirectInfectedContact;
	}
	
	public Integer getNumberOfIndirectInfectedContact() {
		return numberOfIndirectInfectedContact;
	}
	public void setNumberOfIndirectInfectedContact(Integer numberOfIndirectInfectedContact) {
		this.numberOfIndirectInfectedContact = numberOfIndirectInfectedContact;
	}
	
	public Boolean getIsInfected() {
		return isInfected;
	}
	public void setIsInfected(Boolean isInfected) {
		this.isInfected = isInfected;
	}
	
	public Long getInfectedOn() {
		return infectedOn;
	}
	public void setInfectedOn(Long infectedOn) {
		this.infectedOn = infectedOn;
	}
	
	public String getInfectionDeclaredBy() {
		return infectionDeclaredBy;
	}
	public void setInfectionDeclaredBy(String infectionDeclaredBy) {
		this.infectionDeclaredBy = infectionDeclaredBy;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", numberOfDirectInfectedContact=" + numberOfDirectInfectedContact
				+ ", numberOfIndirectInfectedContact=" + numberOfIndirectInfectedContact + ", isInfected=" + isInfected
				+ ", infectedOn=" + infectedOn + ", infectionDeclaredBy=" + infectionDeclaredBy + "]";
	}
}
