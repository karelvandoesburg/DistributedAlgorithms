package BirmanSchiperStephenson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {
	
	private int ID;
	private int amountofprocesses;
	private String host;
	private int amountofmessages;
	
	public Client(int ID, int amountofprocesses, String host) {
		this.ID = ID;
		this.amountofprocesses = amountofprocesses;
		this.host = host;
		this.amountofmessages = 30;
	}
	
	
	public void run() {
		try {
			Process process = new Process(ID,amountofprocesses,host);
			addProcessToRegistry(process);
			Thread.sleep(1000);
			
			for(int i = 0; i < this.amountofmessages; i++) {
				Thread.sleep(Calculate.createRandomNumberBetween(0, 500));
				process.broadcastMessage();
			}
		} 
		catch (RemoteException e) {
			System.out.println("Exception in run in Client: " + e);
			e.printStackTrace();
		} 
		catch (InterruptedException e) {
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
