package RandomizedByzantine;

import java.rmi.Remote;

public interface ServerIF extends Remote{

	public void addProcessToServer(Process process) throws java.rmi.RemoteException;
	public int getNextID() throws java.rmi.RemoteException;
	public void incrementProcesses() throws java.rmi.RemoteException;
	
}
