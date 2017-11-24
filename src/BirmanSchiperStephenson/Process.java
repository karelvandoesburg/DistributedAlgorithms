package BirmanSchiperStephenson;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.PriorityQueue;

public class Process extends UnicastRemoteObject implements IFProcess{

	private static final long serialVersionUID = 1L;
	private Timestamp timestamp;
	private int ID;
	private PriorityQueue<Message> buffer = new PriorityQueue<Message>();
	
	protected Process() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void deliverMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void broadcastMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void incrementOwnTimeStamp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int chooseRandomReceivingProcess() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double createRandomDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String createMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receiveMessage(Message message) {
		// TODO Auto-generated method stub
		
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
	public double createRandomIntervalBetweenMessages() {
		// TODO Auto-generated method stub
		return 0;
	}

}
