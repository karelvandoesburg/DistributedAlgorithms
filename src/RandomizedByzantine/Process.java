package RandomizedByzantine;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Process extends UnicastRemoteObject implements ProcessIF{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private int processID;
	private int v;
	private int round;
	private String messagetype;
	private ArrayList<Message> receivedmessages;
	private String host = "rmi://localhost:1099";
	private int amountofprocesses;
	private int amountoffaultyprocesses;
	
	protected Process() throws RemoteException {
		super();
		this.v = Process.createRandomNumberBetween(0, 1);
		this.round = 1;
		this.messagetype = "N";
		this.receivedmessages = new ArrayList<Message>();
	}
	
	@Override
	public void runRound() {
		this.broadcast();
		this.processNMessages();
	}
	
	public void broadcast() {
		for(int i = 0; i < this.amountofprocesses; i++) {
			if(i != this.processID) {this.createAndSendMessage(i);}
		}
	}
	
	public void processNMessages() {
		this.awaitMessages();
	}
	
	public void createAndSendMessage(int i) {
		try {
			ProcessIF process = Main.getProcess(host, i);
			Message message = createMessage();
			process.receiveMessage(message);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Message createMessage() {
		return new Message(this.messagetype, this.round, this.v);
	}
	
	@Override
	public void receiveMessage(Message message) {
		this.receivedmessages.add(message);
		System.out.println(receivedmessages.size());
		System.out.println(message.toString());
	}
	
	public void awaitMessages() {
		Boolean receivedenoughmessages = false;
		while(!receivedenoughmessages) {
			receivedenoughmessages = this.checkIfEnoughMessagesAreReceived(this.messagetype, this.round);
		}
		System.out.println("process " + this.processID + " has received its messages");
	}
	
	public synchronized Boolean checkIfEnoughMessagesAreReceived(String type, int round) {
		int amountofmessagesthatshouldbereceived = this.amountofprocesses - this.amountoffaultyprocesses;
		int amountofmessagesreceived = 0;
		for(Message message : this.receivedmessages) {
			if(this.compareMessageTypeAndRound(message, type, round)) {
				amountofmessagesreceived++;
				if(amountofmessagesreceived == amountofmessagesthatshouldbereceived) {return true;}
			}
		}
		return false;
	}
	
	public boolean compareMessageTypeAndRound(Message message, String type, int round) {
		return ((message.getMessageType() == type) && (message.getMessageRound() == round));
	}
	
	public void setProcessID(int ID) {
		this.processID = ID;
	}
	
	public int getProcessID() {
		return this.processID;
	}
	
	public static int createRandomNumberBetween(int smallest, int largest) {
		return (int )(Math.random() * ((largest+1)-smallest) + smallest);
	}

	@Override
	public void setAmountOfProcesses(int amountofprocesses) throws RemoteException {
		this.amountofprocesses = amountofprocesses;
	}
	
	@Override
	public void setAmountOfFaultyProcesses(int amountoffaultyprocesses) throws RemoteException {
		this.amountoffaultyprocesses = amountoffaultyprocesses;
	}
	
}
