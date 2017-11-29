package Peterson;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Component extends UnicastRemoteObject implements ComponentIF{
	
	public int componentID;
	public int rightID;
	
	public Component(int componentID, int rightID) throws RemoteException {
		super();
		this.componentID = componentID;
		this.rightID = rightID;
	}

}
