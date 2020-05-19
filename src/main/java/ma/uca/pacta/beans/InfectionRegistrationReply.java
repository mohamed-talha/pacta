package ma.uca.pacta.beans;

public class InfectionRegistrationReply {
	String infectionUserId;
	Long infectionDate;
	String infectionReporterId;
	String directContacts;
	String indirectContacts;
	String requestStatus;
	Integer requestCode;
	
	public InfectionRegistrationReply() {
		super();
	}

	public InfectionRegistrationReply(String infectionUserId, Long infectionDate, String infectionReporterId, String directContacts, String indirectContacts, String requestStatus, Integer requestCode) {
		super();
		this.infectionUserId = infectionUserId;
		this.infectionDate = infectionDate;
		this.infectionReporterId = infectionReporterId;
		this.directContacts = directContacts;
		this.indirectContacts = indirectContacts;
		this.requestStatus = requestStatus;
		this.requestCode = requestCode;
	}

	public String getInfectionUserId() {
		return infectionUserId;
	}

	public void setInfectionUserId(String infectionUserId) {
		this.infectionUserId = infectionUserId;
	}

	public Long getInfectionDate() {
		return infectionDate;
	}

	public void setInfectionDate(Long infectionDate) {
		this.infectionDate = infectionDate;
	}

	public String getInfectionReporterId() {
		return infectionReporterId;
	}

	public void setInfectionReporterId(String infectionReporterId) {
		this.infectionReporterId = infectionReporterId;
	}

	public String getDirectContacts() {
		return directContacts;
	}

	public void setDirectContacts(String directContacts) {
		this.directContacts = directContacts;
	}

	public String getIndirectContacts() {
		return indirectContacts;
	}

	public void setIndirectContacts(String indirectContacts) {
		this.indirectContacts = indirectContacts;
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
