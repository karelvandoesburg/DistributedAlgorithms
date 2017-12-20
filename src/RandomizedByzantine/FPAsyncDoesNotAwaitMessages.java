package RandomizedByzantine;

import java.rmi.RemoteException;

public class FPAsyncDoesNotAwaitMessages extends Process{
	private static final long serialVersionUID = 1L;

	protected FPAsyncDoesNotAwaitMessages() throws RemoteException {
		super();
	}
	
	public void awaitMessages() {
		System.out.println("This process does not await the messages");
	}

}
