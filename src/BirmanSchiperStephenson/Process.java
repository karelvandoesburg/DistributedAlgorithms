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
		System.out.println(message.toString());
	}

	@Override
	public void broadcastMessage() {
		incrementOwnTimeStamp();
		Message message = createMessage();
		message.send();
	}

	@Override
	public void receiveMessage(Message message) {
		System.out.println(message.toString());
	}

	@Override
	public void incrementOwnTimeStamp() {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean compareMessageTimeStampToOwn(Timestamp timestamp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateTimestamp(Timestamp timestamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startSendingMessages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Message createMessage() {
		int receivingprocess = this.chooseRandomReceivingProcess();
		return new Message(receivingprocess,timestamp,host);
	}

}
