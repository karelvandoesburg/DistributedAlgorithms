package BirmanSchiperStephenson;

import java.io.Serializable;
import java.rmi.Naming;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Message implements Runnable, Serializable, Comparable<Message>{

	private int receiverID;
	private String message;
	private Timestamp timestamp;
	private int delay;
	private String host;
	private static final long serialVersionUID = 7526472295622776147L;
	
	public Message(int receiverID, Timestamp timestamp, String host) {
		this.receiverID = receiverID;
		this.timestamp = timestamp;
		this.host = host;
		createRandomDelay();
		createMessageText();
	}
	
	public void send() {
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		try {
			System.out.println("these should follow each other");
			Thread.sleep(delay);
			IFProcess receivingprocess = (IFProcess) getProcessFromRegistry(this.receiverID);
			receivingprocess.receiveMessage(this);
		}
		catch(Exception e) {
			System.out.println("Exception in run in Message: " + e);
			e.printStackTrace();
		}
	}
	
	public IFProcess getProcessFromRegistry(int ID) {
		try{
			String id = Integer.toString(ID);
			IFProcess process = (IFProcess) Naming.lookup(host + "/" + id);
			return process;
		}
		catch(Exception e) {
			System.out.println("Exception in getProcessFromRegistry in Process: " + e);
			e.printStackTrace();
		}
		return null;
	}

	public void createRandomDelay() {
		this.delay = Calculate.createRandomNumberBetween(1,500);
	}
	
	public void createMessageText() {
		this.message = "This message is sent to process " + receiverID + ", with timestamp " + this.timestamp.toString();
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

	@Override
	public int compareTo(Message message) {
		Timestamp timestamp1 = this.timestamp;
		Timestamp timestamp2 = message.getTimestamp();
		if(!timestamp1.isLargerOrEqualToTimestamp(timestamp2)) {return -1;};
		if(timestamp1.isLargerOrEqualToTimestamp(timestamp2)) {return 1;};
		return 0;
	}

}
