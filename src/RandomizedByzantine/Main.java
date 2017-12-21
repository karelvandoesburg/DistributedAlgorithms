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
		
		int amountofprocesses = 10;
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
			Client client = Main.selectFaultyProcess(0);
			new Thread(client).start();
			Thread.sleep(50);
		}
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Press 1 for the synchronous algorithm, press 2 for the asynchronous algorithm");
		int choice = scanner.nextInt();
		
		Process process = new FPNoNValue();

		Main.runChosenAlgorithm(choice,host); 
	}
	
	public static void runMain(int amountofprocesses, int amountoffaultyprocesses, int typeoffaultyprocess) throws InterruptedException {
		
		int port = 1099;
		String host = "rmi://localhost:" + port;
		createLocalRegistry(port);
		
		int amountofnormalprocesses = amountofprocesses - amountoffaultyprocesses;
		
		try {
			Server server = new Server(host,amountoffaultyprocesses);
			Main.addServerToRegistry(server, host);
			
			Thread.sleep(100);
			
			for(int i = 0; i < amountofnormalprocesses; i++) {
				Client client = new Client();
				new Thread(client).start();
				Thread.sleep(50);
			}
			
			for(int i = 0; i < amountoffaultyprocesses; i++) {
				Client client = Main.selectFaultyProcess(0);
				new Thread(client).start();
				Thread.sleep(50);
			}
			
			Scanner scanner = new Scanner(System.in);
			System.out.println("Press 1 for the synchronous algorithm, press 2 for the asynchronous algorithm");
			int choice = scanner.nextInt();
			
			Process process = new FPNoNValue();

			Main.runChosenAlgorithm(choice,host); 
			scanner.close();
		} 
		catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Client selectFaultyProcess(int process) {
		if(process == 0) {
			process = Process.createRandomNumberBetween(1, 6);
		}
		System.out.println("FaultyProcess: " + process);
		Client client = null;
		switch(process) {
			case 1: client = new FPNoBroadcastNClient();
					break;
			case 2: client = new FPNoNValueClient();
					break;
			case 3: client = new FPNoProcessPClient();
					break;
			case 4: client = new FPNothingworksClient();
					break;
			case 5: client = new FPAsyncDoesNotAwaitMessagesClient();
					break;
			case 6: client = new FPDoesNotIncrementRoundClient();
					break;		
		}
		return client;
	}
	
	
	
	
	
	public static void createLocalRegistry(int port) {
		try {
			LocateRegistry.createRegistry(port);
		}
		catch(Exception e) {
			System.out.println("Registry already in use, no need to make another one");
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
	
	public static void closeAlgorithm(String host) throws RemoteException {
		try {
			ServerIF server = Main.getServer(host);
			int amountofprocesses = server.getAmountOfProcesses();
			for(int i = 0; i < amountofprocesses; i++) {
				Naming.unbind(host + "/" + i);
			}
			Naming.unbind(host + "/server");
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
