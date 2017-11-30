package Peterson;

import java.rmi.Remote;
import java.util.ArrayList;

public interface ServerIF extends Remote{

	public void addComponentToServer(Component component) throws java.rmi.RemoteException;
	public void startElection() throws java.rmi.RemoteException;
	public void test() throws java.rmi.RemoteException;
	public ArrayList<Integer> getComponents() throws java.rmi.RemoteException;
	public void addComponent(int ID) throws java.rmi.RemoteException;
	
}
