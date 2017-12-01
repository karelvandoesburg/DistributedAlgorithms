package BirmanSchiperStephenson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {
	
	private int ID;
	private String host = "rmi://127.0.0.1:1099";
	private int amountofmessages = 10;
	
	public Client(int ID) {
		this.ID = ID;
	}
	
	public static void main(String[] args) {
		String host = "rmi://127.0.0.1:1099";
		int id = Client.getRightIdFromServer(host);
		
		Client client = new Client(id);
		new Thread(client).start();
	}
	
	public void run() {
		try {
				int id = Client.getRightIdFromServer(host);
				Process process = new Process(id,amountofmessages, host);
				addProcessToRegistry(process, host);
				this.incrementServerCounter();
				
				Thread.sleep(5000);
				this.updateProcessWithRightAmountofprocesses();
				Thread.sleep(5000);
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
	
	public static int getRightIdFromServer(String host) {
		IFProcess counter = (IFProcess) Message.getProcessFromRegistry(0,host);
		try {
			int ID = counter.getAmountofprocesses() + 1;
			return ID;
		} 
		catch (RemoteException e) {
			e.printStackTrace();
		}
		return Integer.MAX_VALUE;
	}

}
