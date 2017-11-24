package BirmanSchiperStephenson;

import java.rmi.Remote;
import java.util.PriorityQueue;

public interface IFProcess extends Remote {
	
	public void deliverMessage(Message message);
	public void broadcastMessage();
	public void receiveMessage();
	public void incrementOwnTimeStamp();
	public int chooseRandomReceivingProcess();
	public double createRandomDelay();
	public String createMessage();
	public void receiveMessage(Message message);
	public boolean canMessageBeDelivered(Message message);
	public boolean compareMessageTimeStampToOwn(Timestamp timestamp);
	public void updateTimestamp(Timestamp timestamp);
	public void startSendingMessages();
	public double createRandomIntervalBetweenMessages();
	
}
