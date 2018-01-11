package RandomizedByzantine;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws RemoteException, InterruptedException {
		
		//You can select delay in processes with field maximumdelay in the constructor of the Process Class
//		Main.runMain(2, 30, 9, 0);
		Main.onlySetUpMain(1);
		
		
	}
	
	public static void runMain(int SyncorAsync, int amountofprocesses, int amountoffaultyprocesses, int typeoffaultyprocess) throws InterruptedException {
		
		/*
		 * This method will do the whole charade, just fill in what you want in the main method and it runs it for you.
		 * 
		 * SyncorAsync: 1 for synchronous, 2 for asynchronous
		 * typeoffaultyprocess: look at the method selectFaultyProcess in this class to select which process you want from 1 to 6, number 0 means random
		 * 
		 */
		
		int port = 1099;
		String host = "rmi://145.94.181.77:" + port;
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

			Main.runChosenAlgorithm(SyncorAsync,host);
			
		} 
		catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void onlySetUpMain(int amountoffaultyprocesses) {
		
		int port = 1099;
		String host = "rmi://145.94.181.77:1099";
		createLocalRegistry(port);
		
		try {
			Server server = new Server(host, amountoffaultyprocesses);
			Main.addServerToRegistry(server, host);
			
			Scanner scanner = new Scanner(System.in);
			System.out.println("press 1 to run the synchronous server and press 2 to run the asynchronous server");
			int SyncorAsync = scanner.nextInt();

			Main.runChosenAlgorithm(SyncorAsync,host);
			scanner.close();
			
		} 
		catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Client selectFaultyProcess(int process) {
		if(process == 0) {
			process = Process.createRandomNumberBetween(1, 7);
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
			case 7: client = new FPAlwaysSendWrongVClient();
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
