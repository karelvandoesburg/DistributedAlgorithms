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
	private boolean iswaitingformessages;
	private ArrayList<Message> receivedmessages;
	private String host = "rmi://localhost:1099";
	private int amountofprocesses;
	
	protected Process() throws RemoteException {
		super();
		this.v = Process.createRandomNumberBetween(0, 1);
		this.round = 1;
		this.messagetype = "N";
		this.iswaitingformessages = false;
		this.receivedmessages = new ArrayList<Message>();
	}
	
	@Override
	public void runRound() {
		this.broadcast();
//		this.awaitNmessages();
	}
	
	public void broadcast() {
		for(int i = 0; i < this.amountofprocesses; i++) {
			this.createAndSendMessage(i);
		}
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
		System.out.println(message.getMessageType());
		if(this.messagetype.equals(message.getMessageType())) {
			System.out.println("test");
			this.receivedmessages.add(message);
			System.out.println(message.toString());
		}
	}
	
	public void awaitNmessages() {
		this.iswaitingformessages = true;
		Boolean receivedenoughmessages = false;
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
	
}
