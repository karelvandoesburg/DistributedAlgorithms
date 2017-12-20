package RandomizedByzantine;

import java.rmi.Remote;

public interface ProcessIF extends Remote{

	public boolean runRound() throws java.rmi.RemoteException;
	public void receiveMessage(Message message) throws java.rmi.RemoteException;
	public void setAmountOfProcesses(int amountofprocesses) throws java.rmi.RemoteException;
	public void setAmountOfFaultyProcesses(int amountofprocesses) throws java.rmi.RemoteException;
	public void broadcastN() throws java.rmi.RemoteException;
	public void processN() throws java.rmi.RemoteException;
	public void decide() throws java.rmi.RemoteException;
	public void processP() throws java.rmi.RemoteException;
	public boolean isDecided() throws java.rmi.RemoteException;
	public int getDecidedValue() throws java.rmi.RemoteException;
	public void startAsynchronousAlgorithm() throws java.rmi.RemoteException;
	public void stopAsynchronousAlgorithm() throws java.rmi.RemoteException;
	
}
