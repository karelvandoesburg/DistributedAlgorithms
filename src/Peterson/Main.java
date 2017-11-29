package Peterson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import BirmanSchiperStephenson.Client;
import BirmanSchiperStephenson.Process;

public class Main {

public static void main(String[] args) throws RemoteException, InterruptedException {
		
		int port = 1099;
		String host = "rmi://localhost:" + port;
		createLocalRegistry(port);
		int amountofcomponents = 6;
		ArrayList<Integer> componentIDs= new ArrayList<Integer>(amountofcomponents);
		ArrayList<Integer> rightIDs= new ArrayList<Integer>(amountofcomponents);
		
		for(int i = 0; i < amountofcomponents; i++) {
			componentIDs.add(i);
			rightIDs.add(i);
		}
		
		int firstcomponentID = Calculate.createRandomNumberBetween(0, amountofcomponents-1);
		componentIDs.remove(firstcomponentID);
		int rightID = Calculate.createRandomNumberBetween(0, componentIDs.size()-1);
		componentIDs.remove(rightID);
		createComponent(firstcomponentID,rightID);
		int componentID;
		for(int i = 0; i < amountofcomponents-2; i++) {
			int getter = Calculate.createRandomNumberBetween(0, componentIDs.size()-1);
			componentID = rightID;
			rightID = componentIDs.remove(getter);
			createComponent(componentID,rightID);
		}
		createComponent(rightID, firstcomponentID);
		
	}
	
	public static void createLocalRegistry(int port) {
		try {
			LocateRegistry.createRegistry(port);
			System.out.println("Server ready!");
		}
		catch(Exception e) {
			System.out.println("Exception in createLocalRegistry in Main: " + e);
			e.printStackTrace();
		}
	}
	
	public static void createComponent(int componentID, int rightID) {
		Component component = new Component(componentID,rightID);
		new Thread(component).start();
	}
	
//	public void addProcessToRegistry(Component component, String host, int componentID) {
//		try {
//			Naming.bind(host + "/" + Integer.toString(componentID), component);
//			System.out.println("Process " + componentID + " added in " + host + "/" + Integer.toString(componentID));	
//		}
//		
//		catch(Exception e) {
//			System.out.println("Exception in addProcessToRegistry in Client: " + e);
//			e.printStackTrace();
//		}
//	}
	
}
