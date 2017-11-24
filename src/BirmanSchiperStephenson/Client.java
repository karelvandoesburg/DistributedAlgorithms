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
		} 
		catch (RemoteException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	
	public void addProcessToRegistry(Process process) {
		try {
			Naming.bind(host + "/" + Integer.toString(ID), process);
			System.out.println("Process " + ID + " added in " + host + "/" + Integer.toString(ID));
//			
			TimeUnit.SECONDS.sleep(1);
			Process process2 = new Process(11,10,host);
			
			String test = process2.getProcessFromRegistry(5).showYourself();
			
			IFProcess process3 = process2.getProcessFromRegistry(5);
			
			System.out.println(process3.showYourself());
			
		}
		
		catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

}
