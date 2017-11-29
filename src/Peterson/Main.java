package Peterson;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import BirmanSchiperStephenson.Client;

public class Main {

public static void main(String[] args) throws RemoteException, InterruptedException {
		
		int port = 1099;
		String host = "rmi://localhost:" + port;
		createLocalRegistry(port);
		int amountofcomponents = 6;
		ArrayList<Integer> componentIDs= new ArrayList<Integer>(amountofcomponents);
		ArrayList<Integer> rightIDs= new ArrayList<Integer>(amountofcomponents);
		
		for(int i = 1; i <= amountofcomponents; i++) {
			componentIDs.add(i);
			rightIDs.add(i);
		}
		
		for (int i = 0; i < amountofcomponents; i++) {
			int gettercomponentID = Calculate.createRandomNumberBetween(0, componentIDs.size()-1);
			int componentID = componentIDs.remove(gettercomponentID);
			int rightID = componentID;
			int getterrightID = Integer.MAX_VALUE;
			while(rightID == componentID) {
				getterrightID = Calculate.createRandomNumberBetween(0, rightIDs.size()-1);
				rightID = rightIDs.get(getterrightID);
			}
			rightID = rightIDs.remove(getterrightID);
			Component component = new Component(componentID, rightID);
			new Thread(component).start();
		}
		
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
