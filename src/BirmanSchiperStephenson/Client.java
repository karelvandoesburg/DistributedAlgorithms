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
			Process process = new Process(ID,amountofmessages, host);
			addProcessToRegistry(process, host);
			this.incrementServerCounter();
			
			Thread.sleep(1000);
			
			this.updateProcessWithRightAmountofprocesses();
			for(int i = 0; i < this.amountofmessages; i++) {
				this.createIntervalBetweenMessages();
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
	
	public static void addProcessToRegistry(Process process, String host) {
		int ID = process.getProcessID();
		try {
			Naming.bind(host + "/" + Integer.toString(ID), process);
			System.out.println("Process " + ID + " added in " + host + "/" + Integer.toString(ID));	
		}
		
		catch(Exception e) {
			System.out.println("Exception in addProcessToRegistry in Client: " + e);
			e.printStackTrace();
		}
	}
	
	public void createIntervalBetweenMessages() {
		try {
			Thread.sleep(Calculate.createRandomNumberBetween(0, 500));
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void incrementServerCounter() {
		IFProcess counter = (IFProcess) Message.getProcessFromRegistry(0,this.host);
		try {
			counter.incrementAmountofprocesses();
		} 
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void updateProcessWithRightAmountofprocesses() {
		IFProcess counter = (IFProcess) Message.getProcessFromRegistry(0,this.host);
		IFProcess thisprocess = (IFProcess) Message.getProcessFromRegistry(ID,this.host);
		try {
			thisprocess.setAmountofprocesses(counter.getAmountofprocesses());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
