package BirmanSchiperStephenson;

import java.rmi.Remote;
import java.util.PriorityQueue;

public interface IFProcess extends Remote {
	
	public void deliverMessage(Message message) 									throws java.rmi.RemoteException;
	public void broadcastMessage() 													throws java.rmi.RemoteException;
	public void receiveMessage(Message message) 									throws java.rmi.RemoteException;
	public void incrementAmountofprocesses()										throws java.rmi.RemoteException;
	public void setAmountofprocesses(int amountofprocesses)							throws java.rmi.RemoteException;
	public int  getAmountofprocesses()												throws java.rmi.RemoteException;
	
}
