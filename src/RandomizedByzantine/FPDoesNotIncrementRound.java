package RandomizedByzantine;

import java.rmi.RemoteException;

public class FPDoesNotIncrementRound extends Process{
	private static final long serialVersionUID = 1L;

	protected FPDoesNotIncrementRound() throws RemoteException {
		super();
	}
	
	public synchronized void decideNewValueProcess() {
		int newP = this.checkNewValue("P");
		if((newP == 1) | (newP == 0)) {
			this.v = newP;
			this.tryToDecide();
		}
		else {
			this.v = Process.createRandomNumberBetween(0, 1);
		}
		this.messagetype = "N";
	}

}
