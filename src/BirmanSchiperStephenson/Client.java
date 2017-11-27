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
			System.out.println("makank, er gaat iets fout: " + e);
			e.printStackTrace();
		}
		
	}
	
	public void addProcessToRegistry(Process process) {
		try {
			Naming.bind(host + "/" + Integer.toString(ID), process);
			System.out.println("Process " + ID + " added in " + host + "/" + Integer.toString(ID));
			
			TimeUnit.SECONDS.sleep(1);
			
			IFProcess test = (IFProcess) Naming.lookup(host + "/" + 1);
			test.receiveMessage("would be nice if this works");
			
			TimeUnit.SECONDS.sleep(1);
			
			IFProcess test2 = (IFProcess) Naming.lookup(host + "/" + 1);
			test2.deliverMessage();

			
		}
		
		catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

}
