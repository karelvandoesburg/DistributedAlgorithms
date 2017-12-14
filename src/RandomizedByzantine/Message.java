package RandomizedByzantine;

public class Message {

	private String messagetype;
	private int round;
	private int v;
	
	public Message(String messagetype, int round, int v) {
		this.messagetype = messagetype;
		this.round = round;
		this.v = v;
	}
	
	public String getMessageType() {
		return this.messagetype;
	}
	
}
