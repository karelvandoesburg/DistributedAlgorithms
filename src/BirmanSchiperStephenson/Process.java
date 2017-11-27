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
	}

	@Override
	public void deliverMessage(Message message) {
		System.out.println(message.toString());
	}

	@Override
	public void broadcastMessage(Message message) {
		
	}

	@Override
	public void receiveMessage(Message message) {
				
	}

	@Override
	public void incrementOwnTimeStamp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int chooseRandomReceivingProcess() {
		return (int )(Math.random() * this.amountofprocesses + 1); 
	}

	@Override
	public double createRandomDelay() {
		// TODO Auto-generated method stub
		return 0;
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

	@Override
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

	@Override
	public Message createMessage() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
