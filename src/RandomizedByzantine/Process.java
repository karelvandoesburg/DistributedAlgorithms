package RandomizedByzantine;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Process extends UnicastRemoteObject implements ProcessIF, Runnable{

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
	private boolean processisdecided;
	private int decidedvalue;
	private int maximumdelay;
	
	protected Process() throws RemoteException {
		super();
		this.v = Process.createRandomNumberBetween(0, 1);
		this.round = 1;
		this.messagetype = "N";
		this.receivedmessages = new ArrayList<Message>();
		this.decidedvalue = Integer.MIN_VALUE;
		this.processisdecided = false;
		this.maximumdelay = 300;
	}
	
	
	/*
	 * Synchronous code
	 * 
	 */
	@Override
	public void broadcastN() {
		System.out.println(this.createMessage());
		for(int i = 0; i < this.amountofprocesses; i++) {
			if(i != this.processID) {this.createAndSendMessage(i);}
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
	public synchronized void receiveMessage(Message message) {
		this.receivedmessages.add(message);
	}
	
	
	
	
	
	
	@Override
	public void processN() throws RemoteException {
		int amountofmessagesneeded = (this.amountofprocesses+this.amountoffaultyprocesses)/2;
		int valueforP = this.selectNewPValue(amountofmessagesneeded, this.messagetype, this.round);
		this.messagetype = "P";
		this.broadcastP(valueforP);
	}
	
	public synchronized int selectNewPValue(int amount, String type, int round) {
		int value0messages = 0;
		int value1messages = 0;
		for(Message message : this.receivedmessages) {
			if(this.compareMessageTypeAndRound(message, type, round)) {
				if(message.getMessageValue() == 0) {value0messages++;}
				else {value1messages++;}
			}
		}
		return this.choosePvalue(amount, value0messages, value1messages);
	}
	
	public boolean compareMessageTypeAndRound(Message message, String type, int round) {
		return ((message.getMessageType().equals(type)) && (message.getMessageRound() == round));
	}
	
	public int choosePvalue(int amount, int value0messages, int value1messages) {
		if (value0messages > amount) 		{return 0;}
		else if (value1messages > amount) 	{return 1;}
		else 								{return Integer.MIN_VALUE;}
	}
	
	public void broadcastP(int w) {
		System.out.println(this.createMessage(w));
		for(int i = 0; i < this.amountofprocesses; i++) {
			if(i != this.processID) {this.createAndSendMessage(i, w);}
		}
	}
	
	public void createAndSendMessage(int i, int value) {
		try {
			ProcessIF process = Main.getProcess(host, i);
			Message message = createMessage(value);
			process.receiveMessage(message);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized Message createMessage(int value) {
		return new Message(this.messagetype, this.round, value);
	}
	
	
	
	
	
	
	@Override
	public void processP() throws RemoteException {
		this.decideNewValueProcess();
	}
	
	public synchronized void decideNewValueProcess() {
		int newP = this.checkNewValue("P");
		if((newP == 1) | (newP == 0)) {
			this.v = newP;
			this.tryToDecide();
		}
		else {
			this.v = Process.createRandomNumberBetween(0, 1);
		}
		this.round++;
		this.messagetype = "N";
	}
	
	public synchronized int checkNewValue(String type) {
		int amountofneededprocesses = this.amountoffaultyprocesses;
		if(type.equals("Decide")) {amountofneededprocesses = 3*this.amountoffaultyprocesses;}
		int value0messages = 0;
		int value1messages = 0;
		for(Message message : this.receivedmessages) {
			if(this.compareMessageTypeAndRound(message, this.messagetype, this.round)) {
				if(message.getMessageValue() == 0) {value0messages++;}
				else if (message.getMessageValue() == 1) {value1messages++;}
			}
		}
		if(value0messages == value1messages) {return Integer.MIN_VALUE;}
		else if((value0messages > amountofneededprocesses) | (value1messages > amountofneededprocesses)) {
			if(value0messages > value1messages) {return 0;}
			else {return 1;}
		}
		else return Integer.MIN_VALUE;
	}
	
	public void tryToDecide() {
		int decider = this.checkNewValue("Decide");
		if((decider == 0) | (decider == 1)) {
			this.v = decider;
			this.decidedvalue = decider;
			this.processisdecided = true;
		}
	}
	
	
	
	/*
	 * ASynchronous code
	 * 
	 */
	
	@Override
	public void startAsynchronousAlgorithm() {
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		boolean concensus = false;
		while(concensus == false) {
			concensus = this.runRound();
		}
		System.out.println("the number decided by process " + this.processID + " is: " + this.decidedvalue);
	}
	
	@Override
	public boolean runRound() {
		try{
			this.createDelayUpTo(this.maximumdelay);
			this.broadcastN();
			
			this.createDelayUpTo(this.maximumdelay);
			this.awaitMessages();
			
			this.createDelayUpTo(this.maximumdelay);
			this.processN();
			
			this.createDelayUpTo(this.maximumdelay);
			if (this.processisdecided == true) {return true;}
			
			this.createDelayUpTo(this.maximumdelay);
			this.awaitMessages();
			
			this.createDelayUpTo(this.maximumdelay);
			this.processP();
			return false;
		} 
		catch (Exception e) {e.printStackTrace(); return false;}
	}
	
	public void awaitMessages() {
		Boolean receivedenoughmessages = false;
		while(receivedenoughmessages == false) {
			receivedenoughmessages = this.checkIfEnoughMessagesAreReceived(this.messagetype, this.round);
		}
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
	
	
	
	
	
	/*
	 * getters etc.
	 * 
	 */
	public void setProcessID(int ID) {
		this.processID = ID;
	}
	
	public int getProcessID() {
		return this.processID;
	}
	
	public static int createRandomNumberBetween(int smallest, int largest) {
		return (int )(Math.random() * ((largest+1)-smallest) + smallest);
	}
	
	public void createDelayUpTo(int maximum) {
		int sleep = Process.createRandomNumberBetween(0, maximum);
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setAmountOfProcesses(int amountofprocesses) throws RemoteException {
		this.amountofprocesses = amountofprocesses;
	}
	
	@Override
	public void setAmountOfFaultyProcesses(int amountoffaultyprocesses) throws RemoteException {
		this.amountoffaultyprocesses = amountoffaultyprocesses;
	}



	@Override
	public void decide() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized boolean isDecided() throws RemoteException {
		return this.processisdecided;
	}
	
	@Override
	public synchronized int getDecidedValue() throws RemoteException {
		return this.decidedvalue;
	}
	
}
