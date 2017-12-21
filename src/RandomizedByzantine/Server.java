package RandomizedByzantine;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements ServerIF{

	private String host;
	private static final long serialVersionUID = 1L;
	private int amountofprocesses;
	private int amountoffaultyprocesses;
	
	protected Server(String host, int amountoffaultyprocesses) throws RemoteException {
		super();
		this.host = host;
		this.amountofprocesses = 0;
		this.amountoffaultyprocesses = amountoffaultyprocesses;
	}
	
	@Override
	public void runSynchronousAlgorithm() throws RemoteException {
		try {
			this.setAmountOfProcessesInClients();
			Thread.sleep(50);
			Boolean concensus = false;
			while(concensus == false) {
				concensus = this.runSynchronousRound();
			}
			System.out.println("The decided value is: " + Server.showDecidedValue(this.amountofprocesses, this.amountoffaultyprocesses, this.host));
			Main.closeAlgorithm(host);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean runSynchronousRound() {
		this.broadcastNSynchronous();
		this.processNSynchronous();
		boolean concensus = Server.checkIfDecided(this.amountofprocesses,this.amountoffaultyprocesses,this.host);
		if(concensus == true) {return true;}
		this.processPSynchronous();
		return false;
	}
	
	public void broadcastNSynchronous() {
		for(int i = 0; i < amountofprocesses; i++) {
			try {
				ProcessIF process = Main.getProcess(host, i);
				process.broadcastN();
			}
			catch (Exception e) {
				System.out.println("error");
				e.printStackTrace();
			}
		}
	}
	
	public void processNSynchronous() {
		for(int i = 0; i < amountofprocesses; i++) {
			try {
				ProcessIF process = Main.getProcess(host, i);
				process.processN();
			}
			catch (Exception e) {
				System.out.println("error");
				e.printStackTrace();
			}
		}
	}
	
	public synchronized static boolean checkIfDecided(int amountofprocesses, int amountoffaultyprocesses, String host) {
		int totalneeded = amountofprocesses - amountoffaultyprocesses;
		int processesdecided = 0;
		for(int i = 0; i < amountofprocesses; i++) {
			try {
				ProcessIF process = Main.getProcess(host, i);
				if(process.isDecided()) {
					processesdecided++;
				}
			}
			catch (Exception e) {
				System.out.println("error");
				e.printStackTrace();
			}
		}
		return processesdecided >= totalneeded;
	}
	
	public void processPSynchronous() {
		for(int i = 0; i < amountofprocesses; i++) {
			try {
				ProcessIF process = Main.getProcess(host, i);
				process.processP();
			}
			catch (Exception e) {
				System.out.println("error");
				e.printStackTrace();
			}
		}
	}
	
	public synchronized static int showDecidedValue(int amountofprocesses, int amountoffaultyprocesses, String host) {
		int totalneeded = amountofprocesses - amountoffaultyprocesses;
		int value0decided = 0;
		int value1decided = 0;
		for(int i = 0; i < amountofprocesses; i++) {
			try {
				ProcessIF process = Main.getProcess(host, i);
				if(process.isDecided()) {
					if(process.getDecidedValue() == 0) {value0decided++;}
					else {value1decided++;}
				}
			}
			catch (Exception e) {
				System.out.println("error");
				e.printStackTrace();
			}
		}
		if((value0decided | value1decided) >= totalneeded) {
			if(value0decided > value1decided) {return 0;}
			else {return 1;}
		}
		else {return Integer.MIN_VALUE;}
	}
	
	
	
	
	
	@Override
	public void runASynchronousAlgorithm() throws RemoteException {
		this.setAmountOfProcessesInClients();
		for(int i = 0; i < amountofprocesses; i++) {
			try {
				ProcessIF process = Main.getProcess(host, i);
				process.startAsynchronousAlgorithm();
			}
			catch (Exception e) {
				System.out.println("error");
				e.printStackTrace();
			}
		}
		ASynchronousDecider decider = new ASynchronousDecider(this.amountofprocesses, this.amountoffaultyprocesses, this.host);
		new Thread(decider).start();
	}
	
	
	
	
	@Override
	public void addProcessToServer(Process process) {
		try {
			process.setProcessID(amountofprocesses);
			Naming.bind(this.host + "/" + Integer.toString(process.getProcessID()), process);
			System.out.println("Process " + process.getProcessID() + " added in " + host + "/" + Integer.toString(process.getProcessID()));
			amountofprocesses++;
			this.updateFaultyProcesses();
		}
		
		catch(Exception e) {
			System.out.println("Exception in addProcessToRegistry in Client: " + e);
			e.printStackTrace();
		}
	}
	
	public void setAmountOfProcessesInClients() {
		for(int i = 0; i < this.amountofprocesses; i++) {
			try {
				ProcessIF process = Main.getProcess(host, i);
				process.setAmountOfProcesses(this.amountofprocesses);
				process.setAmountOfFaultyProcesses(this.amountoffaultyprocesses);
			}
			catch (Exception e) {
				
			}
		}
	}
	
	@Override
	public int getNextID() {
		return this.amountofprocesses;
	}
	
	@Override
	public int getAmountOfProcesses() {
		return this.amountofprocesses;
	}

	@Override
	public void incrementProcesses() throws RemoteException {
		this.amountofprocesses++;
	}
	
	public void updateFaultyProcesses() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//FAULTY SERVER METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
}
