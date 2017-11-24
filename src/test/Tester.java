package test;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Tester extends UnicastRemoteObject implements TesterIF{
	
	Tester() throws RemoteException {
		super();
	}
	
	public String SayHello() throws RemoteException{
		return("hello");
	}
	
	
}
