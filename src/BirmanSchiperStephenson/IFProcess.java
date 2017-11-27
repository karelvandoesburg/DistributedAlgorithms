package BirmanSchiperStephenson;

import java.rmi.Remote;
import java.util.PriorityQueue;

public interface IFProcess extends Remote {
	
	public void deliverMessage() 									throws java.rmi.RemoteException;
	public void broadcastMessage() 													throws java.rmi.RemoteException;
	public void receiveMessage(String message) 										throws java.rmi.RemoteException;
	public void incrementOwnTimeStamp() 											throws java.rmi.RemoteException;
	public int chooseRandomReceivingProcess() 										throws java.rmi.RemoteException;
	public double createRandomDelay() 												throws java.rmi.RemoteException;
	public String createMessage() 													throws java.rmi.RemoteException;
	public void receiveMessage(Message message) 									throws java.rmi.RemoteException;
	public boolean canMessageBeDelivered(Message message) 							throws java.rmi.RemoteException;
	public boolean compareMessageTimeStampToOwn(Timestamp timestamp) 				throws java.rmi.RemoteException;
	public void updateTimestamp(Timestamp timestamp) 								throws java.rmi.RemoteException;
	public void startSendingMessages() 												throws java.rmi.RemoteException;
	public double createRandomIntervalBetweenMessages() 							throws java.rmi.RemoteException;
	public IFProcess getProcessFromRegistry(int ID) 								throws java.rmi.RemoteException;
	public String showYourself() 													throws java.rmi.RemoteException;
	
}
