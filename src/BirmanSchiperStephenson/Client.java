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
		Process process;
		try {
			process = new Process(ID,amountofprocesses,host);
			
			addProcessToRegistry(process);
			
			Thread.sleep(1000);
			
			for(int i = 0; i < 10; i++) {
				Thread.sleep(Calculate.createRandomNumberBetween(0, 1000));
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
