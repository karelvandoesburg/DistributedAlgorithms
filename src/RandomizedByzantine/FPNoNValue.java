package RandomizedByzantine;

import java.rmi.RemoteException;

public class FPNoNValue extends Process{

	private static final long serialVersionUID = 1L;
	
	protected FPNoNValue() throws RemoteException {
		super();
	}

	public void broadcastN() {
		System.out.println("I won't broadcast N messages");
	}
	
}
