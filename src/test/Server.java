package test;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
	
	public static void main(String[] args) {
		int port = 1099;
		createLocalRegistry(port);
		
		try {
			Tester test = new Tester();
			
			
			Naming.bind("rmi://127.0.0.1:" + port + "/Server", test);
			System.out.println("Server ready - test");
			
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
	}

	public String SayHello() throws RemoteException {
		return "Hello";
	}
	
	public static void createLocalRegistry(int port) {
		try {
			LocateRegistry.createRegistry(port);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}