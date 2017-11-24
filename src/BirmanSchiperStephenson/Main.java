package BirmanSchiperStephenson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import test.Tester;

public class Main {

	public static void main(String[] args) {
		int port = 1099;
		createLocalRegistry(port);
		
		
	}
	
	public static void createLocalRegistry(int port) {
		try {
			LocateRegistry.createRegistry(port);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void startProcess(int amount) {
		
	}
	
}
