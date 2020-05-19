package ma.uca.pacta.beans;

public class Infection {
	String infectionUserId;
	Long infectionDate;
	String infectionReporterId;
	
	public Infection() {
		super();
	}

	public Infection(String infectionUserId, Long infectionDate, String infectionReporterId) {
		super();
		this.infectionUserId = infectionUserId;
		this.infectionDate = infectionDate;
		this.infectionReporterId = infectionReporterId;
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

	@Override
	public String toString() {
		return "Infection [infectionUserId=" + infectionUserId + ", infectionDate=" + infectionDate
				+ ", infectionReporterId=" + infectionReporterId + "]";
	}
}
