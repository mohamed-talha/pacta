package ma.uca.pacta.beans;

public class ContactRegistrationReply {
	String id;
	String sender;
	String requestStatus;
	Integer requestCode;
	
	public ContactRegistrationReply() {
		super();
	}
	
	public ContactRegistrationReply(String id, String sender, String requestStatus, Integer requestCode) {
		super();
		this.id = id;
		this.sender = sender;
		this.requestStatus = requestStatus;
		this.requestCode = requestCode;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
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
