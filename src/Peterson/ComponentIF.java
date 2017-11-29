package Peterson;

import java.rmi.Remote;

public interface ComponentIF extends Remote{

	public int getRightID()								throws java.rmi.RemoteException;
	public int getLeftID()								throws java.rmi.RemoteException;
	public int getMaxID()								throws java.rmi.RemoteException;
	public int getComponentID()							throws java.rmi.RemoteException;
	public void setRightID(int rightID)					throws java.rmi.RemoteException;
	public void setLeftID(int rightID)					throws java.rmi.RemoteException;
	
}
