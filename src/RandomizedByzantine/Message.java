package RandomizedByzantine;

import java.io.Serializable;

public class Message implements Serializable{

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
	
	public int getMessageRound() {
		return this.round;
	}
	
	public String toString() {
		return "(" + this.messagetype + ", " + this.round + ", " + this.v + ")";
	}
	
}
