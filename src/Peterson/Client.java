package Peterson;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class Client implements Runnable {
	
	private int componentID;
	private int rightID;
	private String host;
	
	public Client(int componentID, int rightID, String host) {
		this.componentID = componentID;
		this.rightID = rightID;
		this.host = host;
	}
	
	
	public void run() {
		try {
			Component component = new Component(this.componentID);
			addProcessToRegistry(component);
		} 
		catch (RemoteException e) {
			System.out.println("Exception in run in Client: " + e);
			e.printStackTrace();
		} 
		
	}
	
	public void addProcessToRegistry(Component component) {
		try {
			Naming.bind(host + "/" + Integer.toString(this.componentID), component);
			System.out.println("Component " + componentID + " added in " + host + "/" + Integer.toString(componentID));	
		}
		
		catch(Exception e) {
			System.out.println("Exception in addProcessToRegistry in Client: " + e);
			e.printStackTrace();
		}
	}
	
}
