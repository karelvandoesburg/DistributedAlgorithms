package BirmanSchiperStephenson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.PriorityQueue;

public class Process extends UnicastRemoteObject implements IFProcess{

	private static final long serialVersionUID = 1L;
	private Timestamp timestamp;
	private int ID;
	private int amountofprocesses;
	private String host;
	private PriorityQueue<Message> buffer = new PriorityQueue<Message>();
	
	protected Process(int ID, int amountofprocesses, String host) throws RemoteException {
		super();
		this.ID = ID;
		this.amountofprocesses = amountofprocesses;
		this.host = host;
		this.timestamp = new Timestamp(amountofprocesses);
	}

	@Override
	public synchronized void deliverMessage(Message message) {
		this.timestamp.incrementProcessTimestampByOne(message.getSendingID());
		System.out.println(message.toString() + ". Own timestamp (" + ID + ") is " + this.timestamp.toString());
	}

	@Override
	public synchronized void broadcastMessage() {
		if(ID == 1) {
			incrementOwnTimeStamp();
			Message message = createMessage();
			message.send();
		}
	}

	@Override
	public synchronized void receiveMessage(Message message) {
		if(canMessageBeDelivered(message)) {
			deliverMessage(message);
		}
		else {System.out.println("cannot be delivered");}
	}

	@Override
	public synchronized void incrementOwnTimeStamp() {
		timestamp.incrementProcessTimestampByOne(ID);
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
		timestamp.replaceTimestamp(this.timestamp);
		timestamp.incrementProcessTimestampByOne(message.getSendingID());
		System.out.println("it compares " + timestamp.toString() + " and " + message.getTimestamp().toString());
		return timestamp.isLargerOrEqualToTimestamp(message.getTimestamp());
	}

	@Override
	public void startSendingMessages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized Message createMessage() {
		int receivingprocess = this.chooseRandomReceivingProcess();
		return new Message(ID,receivingprocess,timestamp,host);
	}

}
