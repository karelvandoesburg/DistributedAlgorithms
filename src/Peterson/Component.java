package Peterson;

public class Component implements Runnable{
	
	public int componentID;
	public int rightID;
	
	public Component(int componentID, int rightID) {
		this.componentID = componentID;
		this.rightID = rightID;
	}

	@Override
	public void run() {
		System.out.println("componentID: " + this.componentID + ". And rightID: " + this.rightID);
	}
	

}
