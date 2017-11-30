package Peterson;

import java.rmi.Remote;

public interface ComponentIF extends Remote{

	public int getRightID()								throws java.rmi.RemoteException;
	public void setRightID(int rightID)							throws java.rmi.RemoteException;
	public int getTid()									throws java.rmi.RemoteException;
	public int getNtid()								throws java.rmi.RemoteException;
	public int getNNtid()								throws java.rmi.RemoteException;
	public void setTid(int tid)								throws java.rmi.RemoteException;
	public void setNtid(int ntid)								throws java.rmi.RemoteException;
	public void setNNtid(int nntid)								throws java.rmi.RemoteException;
	public int getComponentID()							throws java.rmi.RemoteException;
	public boolean isActive()							throws java.rmi.RemoteException;
	public void setComponentToRelay()					throws java.rmi.RemoteException;
	
}
