package RandomizedByzantine;

import java.rmi.Remote;

public interface ProcessIF extends Remote{

	public void runRound() throws java.rmi.RemoteException;
	public void receiveMessage(Message message) throws java.rmi.RemoteException;
	public void setAmountOfProcesses(int amountofprocesses) throws java.rmi.RemoteException;
	public void setAmountOfFaultyProcesses(int amountofprocesses) throws java.rmi.RemoteException;
	
}
