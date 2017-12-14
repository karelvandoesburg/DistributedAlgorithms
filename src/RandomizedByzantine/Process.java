package RandomizedByzantine;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Process extends UnicastRemoteObject implements ProcessIF{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private int processID;
	private int v;
	private String host = "rmi://localhost:1099";
	
	protected Process() throws RemoteException {
		super();
	}
	
	public void setProcessID(int ID) {
		this.processID = ID;
	}
	
	public int getProcessID() {
		return this.processID;
	}
	
}
