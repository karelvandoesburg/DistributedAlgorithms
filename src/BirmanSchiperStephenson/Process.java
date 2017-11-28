package BirmanSchiperStephenson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Process extends UnicastRemoteObject implements IFProcess{

	private static final long serialVersionUID = 1L;
	private Timestamp timestamp;
	private int ID;
	private int amountofprocesses;
	private String host;
	private ArrayList<Message> buffer = new ArrayList<Message>();
	
	protected Process(int ID, int amountofprocesses, String host) throws RemoteException {
		super();
		this.ID = ID;
		this.amountofprocesses = amountofprocesses;
		this.host = host;
		this.timestamp = new Timestamp(amountofprocesses);
	}

	@Override
	public synchronized void broadcastMessage() {
		incrementOwnTimeStamp();
		sendMessageToAllProcesses();
	}
	
	@Override
	public synchronized void receiveMessage(Message message) {
		if(canMessageBeDelivered(message)) {
			deliverMessage(message);
		}
		else {
			placeInBuffer(message);
			deliverMessagesBuffer();
			System.out.println(message.getTimestamp().toString() + "Sent from " + message.getSendingID() + ". Is placed in buffer. Number of message in buffer of process " + ID + " is: " + this.buffer.size());
		}
	}
	
	@Override
	public synchronized void deliverMessage(Message message) {
		this.updateTimestampAfterDelivery(message);
		printMessage(message);
		deliverMessagesBuffer();
	}
	
	public synchronized void deliverMessagesBuffer() {
		for(int i = 0; i < buffer.size(); i++) {
			Message message = buffer.get(i);
			if(this.canMessageBeDelivered(message) || message.isOverBufferTime()) {
				message = buffer.remove(i);
				System.out.println("Following delivery is from buffer " + ID + ":");
				this.deliverMessage(message);
			}
		}
	}
	
	
	
	
	public void sendMessageToAllProcesses() {
		for(int i = 0; i < this.amountofprocesses; i++) {
			Message message = createMessage(i+1);
			message.send();
		}
	}
	
	@Override
	public void incrementOwnTimeStamp() {
		timestamp.incrementProcessTimestampByOne(ID);
	}
	
	public Message createMessage(int receivingprocessID) {
		return new Message(ID,receivingprocessID,timestamp,host);
	}
	
	public int chooseRandomReceivingProcess() {
		int res = this.ID;
		while(res == this.ID) {
			res = Calculate.createRandomNumberBetween(1,amountofprocesses); 
		}
		return res;
	}
	
	@Override
	public boolean canMessageBeDelivered(Message message) {
		Timestamp timestamp = new Timestamp(this.amountofprocesses);
		timestamp.replaceWithTimestamp(this.timestamp);
		timestamp.incrementProcessTimestampByOne(message.getSendingID());
		return timestamp.isLargerOrEqualToTimestamp(message.getTimestamp());
	}
	
	public void placeInBuffer(Message message) {
		long now = Instant.now().toEpochMilli();
		message.setTimetobuffer(now);
		buffer.add(message);
		this.deliverMessagesBuffer();
	}
	
	public void updateTimestampAfterDelivery(Message message) {
		Timestamp timestamp2 = message.getTimestamp();
		for(int i = 0; i < timestamp.getTimevector().length; i++) {
			timestamp.getTimevector()[i] = Math.max(timestamp.getTimevector()[i], timestamp2.getTimevector()[i]);
		}
	}
	
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	public void printMessage(Message message) {
		System.out.println(message.toString() + ". New timestamp of process " + ID + " is " + this.timestamp.toString());
	}

}
