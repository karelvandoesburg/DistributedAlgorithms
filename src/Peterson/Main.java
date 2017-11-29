package Peterson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

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
		System.out.println(firstcomponentID);
		componentIDs.remove(firstcomponentID);
		int getter = Calculate.createRandomNumberBetween(0, componentIDs.size()-1);
		int rightID = componentIDs.remove(getter);
		Client client = new Client(firstcomponentID,rightID,host);
		new Thread(client).start();
		int componentID;
		for(int i = 0; i < amountofcomponents-2; i++) {
			getter = Calculate.createRandomNumberBetween(0, componentIDs.size()-1);
			componentID = rightID;
			rightID = componentIDs.remove(getter);
			System.out.println(componentID);
			client = new Client(componentID,rightID,host);
			new Thread(client).start();
		}
		System.out.println(rightID);
		client = new Client(rightID,firstcomponentID,host);
		new Thread(client).start();
		
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
	
}
