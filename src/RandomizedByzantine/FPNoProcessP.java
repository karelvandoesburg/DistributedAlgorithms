package RandomizedByzantine;

import java.rmi.RemoteException;

public class FPNoProcessP extends Process{
	private static final long serialVersionUID = 1L;

	protected FPNoProcessP() throws RemoteException {
		super();
	}
	
	public void processP() throws RemoteException {
		System.out.println("This process does not process P");
	}

}
