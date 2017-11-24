package BirmanSchiperStephenson;

import java.rmi.Naming;
import java.rmi.RemoteException;

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
		
		Process process;
		try {
			process = new Process(ID,amountofprocesses,host);
			addProcessToRegistry(process);
		} 
		catch (RemoteException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	
	public void addProcessToRegistry(Process process) {
		try {
			Naming.bind(host + "/" + Integer.toString(ID), process);
			System.out.println("Process " + ID + " added");
		}
		catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

}
