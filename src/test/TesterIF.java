package test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TesterIF extends Remote{

	//every remote method must throw a remoteexception
	public String SayHello() throws RemoteException;
	
}
