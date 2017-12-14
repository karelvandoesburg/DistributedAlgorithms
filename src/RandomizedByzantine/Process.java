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
	
	protected Process() throws RemoteException {
		super();
		this.v = Process.createRandomNumberBetween(0, 1);
		this.round = 1;
		this.messagetype = "N";
		this.iswaitingformessages = false;
	}
	
	@Override
	public void runRound() {
		this.broadcast();
//		this.awaitNmessages();
	}
	
	public void broadcast() {
		try {
			ServerIF server = Main.getServer(host);
			int amountofprocesses = server.getAmountOfProcesses();
			for(int i = 0; i < amountofprocesses; i++) {
				this.sendMessage(i);
			}
		}
		catch (Exception e) {
			System.out.println("Can't get the server from the RMI in broadcast method");
			e.printStackTrace();
		}
	}
	
	public void sendMessage(int i) {
		try {
			ProcessIF process = Main.getProcess(host, i);
			Message message = createMessage();
			process.receiveMessage(message);
		}
		catch (Exception e) {
			
		}
	}
	
	public Message createMessage() {
		return new Message(this.messagetype, this.round, this.v);
	}
	
	@Override
	public void receiveMessage(Message message) {
		if(this.iswaitingformessages && (this.messagetype == message.getMessageType())) {
			this.receivedmessages.add(message);
		}
	}
	
	public void awaitNmessages() {
		this.iswaitingformessages = true;
		Boolean receivedenoughmessages = false;
//		while()
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
	
}
