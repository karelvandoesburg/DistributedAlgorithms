package BirmanSchiperStephenson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {

	public static void main(String[] args) {
		int port = 1099;
		createLocalRegistry(port);
		int amountofprocesses = 10;
		
		for (int i = 0; i < amountofprocesses; i++) {
			Client client = new Client(i+1,10);
			new Thread(client).start();
		}
	}
	
	public static void createLocalRegistry(int port) {
		try {
			LocateRegistry.createRegistry(port);
			System.out.println("Server ready!");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void startProcess(int amount) {
		
	}
	
}
