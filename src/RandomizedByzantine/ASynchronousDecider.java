package RandomizedByzantine;

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
			Thread.sleep(1500);
			int decider = Server.showDecidedValue(amountofprocesses, amountoffaultyprocesses, host);
			System.out.println("The decided value is: " + decider);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
