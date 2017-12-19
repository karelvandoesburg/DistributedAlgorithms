package RandomizedByzantine;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws RemoteException, InterruptedException {
		
		int port = 1099;
		String host = "rmi://localhost:" + port;
		createLocalRegistry(port);
		
		int amountofprocesses = 7;
		int amountoffaultyprocesses = 1;
		int amountofnormalprocesses = amountofprocesses - amountoffaultyprocesses;
		
		Server server = new Server(host,amountoffaultyprocesses);
		Main.addServerToRegistry(server, host);
		
		Thread.sleep(100);
		
		for(int i = 0; i < amountofnormalprocesses; i++) {
			Client client = new Client();
			new Thread(client).start();
			Thread.sleep(50);
		}
		
		for(int i = 0; i < amountoffaultyprocesses; i++) {
			FPNoBroadcastNClient client = new FPNoBroadcastNClient();
			new Thread(client).start();
			Thread.sleep(50);
		}
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Press 1 for the synchronous algorithm, press 2 for the asynchronous algorithm");
		int choice = scanner.nextInt();

		Main.runChosenAlgorithm(choice,host); 
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
	
	public static void runChosenAlgorithm(int choice, String host) {
		ServerIF server = Main.getServer(host);
		try {
			if(choice == 1) {
				server.runSynchronousAlgorithm();
			}
			if(choice == 2) {
				server.runASynchronousAlgorithm();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ServerIF getServer(String host) {
		try {
			ServerIF server = (ServerIF) Naming.lookup(host + "/server");
			return server;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ProcessIF getProcess(String host, int processID) {
		try {
			ProcessIF process = (ProcessIF) Naming.lookup(host + "/" + processID);
			return process;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
