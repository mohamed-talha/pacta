package ma.uca.pacta.beans;

public class AccountRegistrationReply {
	
	String id;
	Integer numberOfDirectInfectedContact;
	Integer numberOfIndirectInfectedContact;
	Boolean isInfected;
	String requestStatus;
	Integer requestCode;

	public AccountRegistrationReply() {
		super();
	}
	
	public AccountRegistrationReply(String id, Integer numberOfDirectInfectedContact, Integer numberOfIndirectInfectedContact, Boolean isInfected, String requestStatus, Integer requestCode) {
		super();
		this.id = id;
		this.numberOfDirectInfectedContact = numberOfDirectInfectedContact;
		this.numberOfIndirectInfectedContact = numberOfIndirectInfectedContact;
		this.isInfected = isInfected;
		this.requestStatus = requestStatus;
		this.requestCode = requestCode;
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
	
	public String getRequestStatus() {
		return requestStatus;
	}
	
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	
	public Integer getRequestCode() {
		return requestCode;
	}
	
	public void setRequestCode(Integer requestCode) {
		this.requestCode = requestCode;
	}	
}
