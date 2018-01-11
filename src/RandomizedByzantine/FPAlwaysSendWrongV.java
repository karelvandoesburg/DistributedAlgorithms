package RandomizedByzantine;

import java.rmi.RemoteException;

public class FPAlwaysSendWrongV extends Process{

	private static final long serialVersionUID = 1L;
	
	protected FPAlwaysSendWrongV() throws RemoteException {
		super();
	}

	@Override
	public void broadcastN() {
		System.out.println("");
		System.out.println("");
		System.out.println("ROUND: " + this.round);
		this.v = this.createRandomNumberBetween(0, 1);
		System.out.println("Sending N message: " + this.createMessage());
		for(int i = 0; i < this.amountofprocesses; i++) {
			this.createAndSendMessage(i);
		}
	}
	
}
