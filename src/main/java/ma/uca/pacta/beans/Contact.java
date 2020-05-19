package ma.uca.pacta.beans;

public class Contact {
	String id;
	String sender;
	
	public Contact() {
		super();
	}

	
	public Contact(String id, String sender) {
		super();
		this.id = id;
		this.sender = sender;
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
	
	@Override
	public String toString() {
		return "Contact [id=" + id + ", sender=" + sender + "]";
	}
}
