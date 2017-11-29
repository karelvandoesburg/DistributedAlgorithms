package Peterson;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Component extends UnicastRemoteObject implements ComponentIF{
	
	public int componentID;
	public int rightID;
	public int leftID;
	public int MaxID;
	
	public Component(int componentID) throws RemoteException {
		super();
		this.componentID = componentID;
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
	public void setLeftID(int leftID) {
		this.leftID = leftID;
	}

	@Override
	public int getLeftID() throws RemoteException {
		return this.leftID;
	}

	@Override
	public int getMaxID() throws RemoteException {
		return this.MaxID;
	}

}
