package test;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	public static void main(String[] args) {
		
		String host = "rmi://127.0.0.1:1099";
		
        try {
            TesterIF stub = (TesterIF) Naming.lookup(host + "/Server");
            String response = stub.SayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
		
	}
	
}
