package BirmanSchiperStephenson;

import java.util.concurrent.TimeUnit;

public class Message implements Runnable{

	private int receiverID;
	private String message;
	private Timestamp timestamp;
	private int delay;
	
	public Message(int receiverID, Timestamp timestamp, int delay) {
		this.receiverID = receiverID;
		this.timestamp = timestamp;
		this.delay = delay;
		createMessageText();
	}
	
	public void send() {
		this.run();
	}
	
	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(delay);
			
		}
		catch(Exception e) {
			System.out.println("Exception in run in Message: " + e);
			e.printStackTrace();
		}
	}
	
	public void createMessageText() {
		this.message = "This message is sent to process " + receiverID + ", with timestamp " + timestamp.toString();
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
