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
	public synchronized void broadcastMessage() {
		incrementOwnTimeStamp();
		Message message = createMessage();
		message.send();
	}

	@Override
	public synchronized void receiveMessage(Message message) {
		
	}

	@Override
	public void incrementOwnTimeStamp() {
		timestamp.incrementProcessTimestampByOne(ID);
	}

	@Override
	public int chooseRandomReceivingProcess() {
		int res = this.amountofprocesses;
		while(res == this.amountofprocesses) {
			res = (int )(Math.random() * this.amountofprocesses + 1); 
		}
		return res;
	}
	
	public int createRandomNumberBetween(int smallest, int largest) {
		return (int )(Math.random() * ((largest+1)-smallest) + smallest);
	}

	public int createRandomDelay() {
		return this.createRandomNumberBetween(1,5);
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
	public IFProcess getRandomProcessFromRegistry() throws RemoteException{
		int randomprocess = this.chooseRandomReceivingProcess();
		return getProcessFromRegistry(randomprocess);
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
	public Message createMessage() {
		int delay = this.createRandomDelay();
		return new Message(ID,timestamp,delay);
	}

}
