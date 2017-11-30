package BirmanSchiperStephenson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws RemoteException, InterruptedException {
		
		int port = 1099;
		String host = "rmi://127.0.0.1:" + port;
		createLocalRegistry(port);
		int amountofprocesses = 6;
		
		Process counter = new Process(0,0,host);
		Client.addProcessToRegistry(counter, host);
		
//		for (int i = 0; i < amountofprocesses; i++) {
//			Client client = new Client(i+1);
//			new Thread(client).start();
//		}
		
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
