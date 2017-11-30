package Peterson;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Component extends UnicastRemoteObject implements ComponentIF{
	
	public int componentID;
	public int rightID;
	public int tid;
	public int ntid;
	public int nntid;
	private boolean isactive;
	
	public Component(int componentID) throws RemoteException {
		super();
		this.componentID = componentID;
		this.tid = componentID;
		this.isactive = true;
		this.ntid = Integer.MAX_VALUE;
		this.nntid = Integer.MAX_VALUE;
	}
	
	@Override
	public int getComponentID() {
		return this.componentID;
	}

	@Override
	public int getRightID() {
		return this.rightID;
	}

	@Override
	public void setRightID(int rightID) {
		this.rightID = rightID;		
	}

	@Override
	public boolean isActive() throws RemoteException {
		return this.isactive;
	}

	@Override
	public void setComponentToRelay() throws RemoteException {
		this.isactive = false;
	}

	@Override
	public int getTid() throws RemoteException {
		return this.tid;
	}

	@Override
	public int getNtid() throws RemoteException {
		return this.ntid;
	}

	@Override
	public int getNNtid() throws RemoteException {
		return this.nntid;
	}

	@Override
	public void setTid(int tid) throws RemoteException {
		this.tid = tid;
	}

	@Override
	public void setNtid(int ntid) throws RemoteException {
		this.ntid = ntid;
	}

	@Override
	public void setNNtid(int nntid) throws RemoteException {
		this.nntid = nntid;
	}

	@Override
	public String componentToString() throws RemoteException {
		return "Component: " + this.componentID  + ", tid: " + this.tid + ", ntid: " + this.ntid + ", nntid: " + this.nntid + ", active: " + this.isactive;
	}

}
