package RandomizedByzantine;

import java.rmi.RemoteException;

public class FPNothingworks extends Process{
	
	private static final long serialVersionUID = 1L;

	protected FPNothingworks() throws RemoteException {
		super();
	}
	
	public void broadcastN() {
		System.out.println("This process does nothing");
	}
	
	public void processN() throws RemoteException {
		System.out.println("This process does nothing");
	}
	
	public void processP() throws RemoteException {
		System.out.println("This process does nothing");
	}
	
	public void displayInitialValue() {
		System.out.println("This is a faulty process");
	}

}
