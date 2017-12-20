package RandomizedByzantine;

import java.rmi.Naming;

public class ASynchronousDecider implements Runnable{

	private int amountoffaultyprocesses;
	private int amountofprocesses;
	private String host;
	
	public ASynchronousDecider(int amountofprocesses, int amountoffaultyprocesses, String host) {
		this.amountoffaultyprocesses = amountoffaultyprocesses;
		this.amountofprocesses = amountofprocesses;
		this.host = host;
	}

	@Override
	public void run() {
		boolean isdecided = false;
		while(isdecided == false) {
			isdecided = Server.checkIfDecided(amountofprocesses, amountoffaultyprocesses, host);
		}
		try {
			this.stopAllThreads();
			Thread.sleep(1500);
			int decider = Server.showDecidedValue(amountofprocesses, amountoffaultyprocesses, host);
			System.out.println("The decided value is: " + decider);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void stopAllThreads() {
		for(int i = 0; i < this.amountofprocesses; i++) {
			try {
				ProcessIF process = (ProcessIF) Naming.lookup(host + "/" + i);
				process.stopAsynchronousAlgorithm();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
