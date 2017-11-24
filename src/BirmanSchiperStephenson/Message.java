package BirmanSchiperStephenson;

public class Message {

	private int receiverID;
	private String message;
	private Timestamp timestamp;
	
	public Message(int receiverID, Timestamp timestamp) {
		this.receiverID = receiverID;
		this.timestamp = timestamp;
	}
	
	public String toString() {
		return this.message;
	}
	
	public int getReceiverID() {
		return receiverID;
	}
	
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
}
