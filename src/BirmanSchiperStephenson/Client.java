package BirmanSchiperStephenson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {
	
	private int ID;
	private int amountofprocesses;
	private String host;
	
	public Client(int ID, int amountofprocesses, String host) {
		this.ID = ID;
		this.amountofprocesses = amountofprocesses;
		this.host = host;
	}
	
	
	public void run() {
		System.out.println("the start of the clients should follow each other");
		Process process;
		try {
			process = new Process(ID,amountofprocesses,host);
			addProcessToRegistry(process);
			
			
			for(int i = 0; i < 3; i++) {
				process.broadcastMessage();
			}
		} 
		catch (RemoteException e) {
			System.out.println("Exception in run in Client: " + e);
			e.printStackTrace();
		} 
		
	}
	
	public void addProcessToRegistry(Process process) {
		try {
			Naming.bind(host + "/" + Integer.toString(ID), process);
			System.out.println("Process " + ID + " added in " + host + "/" + Integer.toString(ID));	
		}
		
		catch(Exception e) {
			System.out.println("Exception in addProcessToRegistry in Client: " + e);
			e.printStackTrace();
		}
	}

}
