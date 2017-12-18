package RandomizedByzantine;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements ServerIF{

	private String host;
	private static final long serialVersionUID = 1L;
	private int amountofprocesses;
	private int amountoffaultyprocesses;
	
	protected Server(String host) throws RemoteException {
		super();
		this.host = host;
		this.amountofprocesses = 0;
	}
	
	@Override
	public void runSynchronousAlgorithm() throws RemoteException {
		try {
			Thread.sleep(50);
			this.setAmountOfProcessesInClients();
			Boolean concensus = false;
//			while(concensus == false) {
				this.runSynchronousRound(concensus);
//			}
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void runSynchronousRound(Boolean concensus) {
		this.broadcastNSynchronous();
		this.processNSynchronous();
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
	
	
	
	
	@Override
	public void runASynchronousAlgorithm() throws RemoteException {
		
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
		this.amountoffaultyprocesses = amountofprocesses/5;
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

}
