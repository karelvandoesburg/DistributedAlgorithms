package BirmanSchiperStephenson;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Process extends UnicastRemoteObject implements IFProcess{

	private int messageID;
	private static final long serialVersionUID = 1L;
	private Timestamp timestamp;
	private int processID;
	private int amountofprocesses;
	private String host;
	private ArrayList<Message> buffer = new ArrayList<Message>();
	private PrintWriter printwriter;
	private int amountofmessages;
	private RecordCausalOrdening recordcausalordening;
	
	protected Process(int ID, int amountofprocesses, int amountofmessages, String host) throws RemoteException {
		super();
		this.processID = ID;
		this.messageID = 0;
		this.amountofprocesses = amountofprocesses;
		this.host = host;
		this.timestamp = new Timestamp(amountofprocesses);
		this.amountofmessages = amountofmessages;
		this.recordcausalordening = new RecordCausalOrdening(amountofprocesses);
		installPrintwriter();
	}

	@Override
	public synchronized void broadcastMessage() {
		messageID++;
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
			System.out.println(message.getTimestamp().toString() + "Sent from " + message.getSendingID() + ". Is placed in buffer. Number of message in buffer of process " + processID + " is: " + this.buffer.size());
		}
	}
	
	@Override
	public synchronized void deliverMessage(Message message) {
		this.updateTimestampAfterDelivery(message);
		printMessageToConsole(message);
		printMessageToFile(message);
		processCausalOrdening(message);
		deliverMessagesBuffer();
	}
	
	public synchronized void deliverMessagesBuffer() {
		for(int i = 0; i < buffer.size(); i++) {
			Message message = buffer.get(i);
			if(this.canMessageBeDelivered(message) || message.isOverBufferTime()) {
				message = buffer.remove(i);
				System.out.println("Following delivery is from buffer " + processID + ":");
				this.deliverMessage(message);
			}
		}
	}
	
	
	
	
	
	public void sendMessageToAllProcesses() {
		for(int i = 0; i < this.amountofprocesses; i++) {
			if(i+1 != this.processID) {
				Message message = createMessage(i+1);
				message.send();
			}
		}
	}
	
	public void incrementOwnTimeStamp() {
		timestamp.incrementProcessTimestampByOne(processID);
	}
	
	public Message createMessage(int receivingprocessID) {
		return new Message(this.messageID, processID,receivingprocessID,timestamp,host);
	}
	
	public boolean canMessageBeDelivered(Message message) {
		Timestamp timestamp = this.incrementOwnTimestampToMakeComparison(message);
		return timestamp.isLargerOrEqualToTimestamp(message.getTimestamp());
	}
	
	public Timestamp incrementOwnTimestampToMakeComparison(Message message) {
		Timestamp timestamp = new Timestamp(this.amountofprocesses);
		timestamp.replaceWithTimestamp(this.timestamp);
		timestamp.incrementProcessTimestampByOne(message.getSendingID());
		return timestamp;
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
	
	public void installPrintwriter() {
		try {
			this.printwriter = new PrintWriter("MessagesProccess" + this.processID + ".txt");
		}
		catch(IOException e) {
			System.out.println("Exception in install printwriter: " + e);
			e.printStackTrace();
		}
	}
	
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	public String messageToOutputString(Message message) {
		return message.toString() + ". New timestamp of process " + processID + " is " + this.timestamp.toString();
	}
	
	public void printMessageToConsole(Message message) {
		System.out.println(this.messageToOutputString(message));
	}
	
	public void printMessageToFile(Message message) {
		printwriter.println(this.messageToOutputString(message));
		printwriter.flush();
	}
	
	public void processCausalOrdening(Message message) {
		String iscausalornot = this.recordcausalordening.incrementCounter(message);
		printwriter.println(iscausalornot);
		printwriter.println();
		printwriter.flush();
	}

}
