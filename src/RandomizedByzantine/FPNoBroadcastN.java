package RandomizedByzantine;

import java.rmi.RemoteException;

public class FPNoBroadcastN extends Process{

	private static final long serialVersionUID = 1L;
	
	protected FPNoBroadcastN() throws RemoteException {
		super();
	}

	public void broadcastN() {
		System.out.println("I won't broadcast N messages");
	}
	
}
