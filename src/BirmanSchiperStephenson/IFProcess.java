package BirmanSchiperStephenson;

import java.rmi.Remote;
import java.util.PriorityQueue;

public interface IFProcess extends Remote {
	
	public void deliverMessage(Message message) 									throws java.rmi.RemoteException;
	public void broadcastMessage() 													throws java.rmi.RemoteException;
	public void receiveMessage(Message message) 									throws java.rmi.RemoteException;
	public void incrementOwnTimeStamp() 											throws java.rmi.RemoteException;
	public int chooseRandomReceivingProcess() 										throws java.rmi.RemoteException;
	public Message createMessage() 													throws java.rmi.RemoteException;
	public boolean canMessageBeDelivered(Message message) 							throws java.rmi.RemoteException;
	public boolean compareMessageTimeStampToOwn(Timestamp timestamp) 				throws java.rmi.RemoteException;
	public void updateTimestamp(Timestamp timestamp) 								throws java.rmi.RemoteException;
	public void startSendingMessages() 												throws java.rmi.RemoteException;
	
}
