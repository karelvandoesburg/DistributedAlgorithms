package BirmanSchiperStephenson;

public class Client implements Runnable {
	
	private int ID;
	private int amountofprocesses;
	
	public Client(int ID, int amountofprocesses) {
		this.ID = ID;
		this.amountofprocesses = amountofprocesses;
	}
	
	
	public void run() {
		
		System.out.println("I am client: " + this.ID);
		
	}

}
