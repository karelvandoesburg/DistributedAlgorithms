package Peterson;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client implements Runnable {
	
	private int componentID;
	private int rightID;
	private String host;
	
	public Client(int componentID, String host) {
		this.componentID = componentID;
		this.host = host;
	}
	
	
	public void run() {
		try {
			Component component = new Component(this.componentID);
			ServerIF serverready;
			try {
				serverready = (ServerIF) Naming.lookup(host + "/server");
				serverready.addComponentToServer(component);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
