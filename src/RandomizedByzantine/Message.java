package RandomizedByzantine;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	private String messagetype;
	private int round;
	private int v;
	private int sendingID;
	
	public Message(String messagetype, int round, int v, int sendingID) {
		this.messagetype = messagetype;
		this.round = round;
		this.v = v;
		this.sendingID = sendingID;
	}
	
	public String getMessageType() {
		return this.messagetype;
	}
	
	public int getMessageRound() {
		return this.round;
	}
	
	public int getMessageValue() {
		return this.v;
	}
	
	public int getSendingID() {
		return this.sendingID;
	}
	
	public String toString() {
		return "(" + this.messagetype + ", " + this.round + ", " + this.v + ")";
	}
	
}
