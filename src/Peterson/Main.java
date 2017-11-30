package Peterson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import com.sun.glass.ui.Timer;

public class Main {

public static void main(String[] args) throws RemoteException, InterruptedException {
		
		int port = 1099;
		String host = "rmi://localhost:" + port;
		createLocalRegistry(port);
		int amountofcomponents = 6;
		
		Server server = new Server(host);
		
		for(int i = 0; i < amountofcomponents; i++) {
			Component component = new Component(i);
			server.addComponentToServer(component);
		}
		
		Thread.sleep(1000);
		
//		for(int i = 0; i < amountofcomponents; i++) {
//			ComponentIF neighbour = (ComponentIF) Server.getComponentFromServer(i, host);
//			System.out.println("component " + neighbour.getComponentID() + " has right ID " + neighbour.getRightID() + " left " + neighbour.getLeftID());
//		}
		
		server.startElection();
		
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
