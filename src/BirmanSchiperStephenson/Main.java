package BirmanSchiperStephenson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {

	public static void main(String[] args) throws RemoteException {
		int port = 1099;
		String host = "rmi://localhost:" + port;
		createLocalRegistry(port);
		int amountofprocesses = 10;
		
		for (int i = 0; i < amountofprocesses; i++) {
			Client client = new Client(i+1,amountofprocesses,host);
			new Thread(client).start();
		}
		
		Process test = new Process(11,10,host);
		
		System.out.println("hallo");
		System.out.println(test.getProcessFromRegistry(5));
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
	
}
