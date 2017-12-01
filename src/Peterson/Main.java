package Peterson;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import com.sun.glass.ui.Timer;

public class Main {

public static void main(String[] args) throws RemoteException, InterruptedException {
		
		int port = 1099;
		String host = "rmi://localhost:" + port;
		createLocalRegistry(port);
		int amountofcomponents = 30;
		
		Server server = new Server(host);
		Main.addServerToRegistry(server, host);
		
		Thread.sleep(1000);
		
		try {	
			for(int i = 0; i < amountofcomponents; i++) {
				Component component = new Component(i);
				new Thread(component).start();
				Thread.sleep(40);
			}
			ServerIF serverready = (ServerIF) Naming.lookup(host + "/server");
			serverready.startElection();
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
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
	
	public static void addComponentToRegistry(Component component, String host) {
		try {
			Naming.bind(host + "/" + Integer.toString(component.getComponentID()), component);
			System.out.println("Component " + component.getComponentID() + " added in " + host + "/" + Integer.toString(component.getComponentID()));	
		}
		
		catch(Exception e) {
			System.out.println("Exception in addProcessToRegistry in Client: " + e);
			e.printStackTrace();
		}
	}
	
}
