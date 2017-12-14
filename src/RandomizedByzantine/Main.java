package RandomizedByzantine;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {

public static void main(String[] args) throws RemoteException, InterruptedException {
		
		int port = 1099;
		String host = "rmi://localhost:" + port;
		createLocalRegistry(port);
		
		Server server = new Server(host);
		Main.addServerToRegistry(server, host);
		
		Thread.sleep(1000);

	}
	
	public static void createLocalRegistry(int port) {
		try {
			LocateRegistry.createRegistry(port);
		}
		catch(Exception e) {
			System.out.println("Exception in createLocalRegistry in Main: " + e);
			e.printStackTrace();
		}
	}
	
	public static void addServerToRegistry(Server server, String host) {
		try {
			Naming.bind(host + "/server", server);
			System.out.println("Server ready");	
		}
		
		catch(Exception e) {
			System.out.println("Exception in addServerToRegistry in Client: " + e);
			e.printStackTrace();
		}
	}
	
}
