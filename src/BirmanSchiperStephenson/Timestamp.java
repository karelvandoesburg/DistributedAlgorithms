package BirmanSchiperStephenson;

import java.io.Serializable;

public class Timestamp implements Serializable {
	private int[] timevector;
	private static final long serialVersionUID = 1326472295622776147L;
	
	public Timestamp(int newVector){
		timevector = new int[newVector];
		for(int i = 0; i < newVector; i++) {
			timevector[i] = 0;
		}
	}
	
	public boolean isLargerOrEqualToTimestamp (Timestamp othertimestamp) {
		for (int i=0; i< timevector.length;i++)
			if (timevector[i] < othertimestamp.timevector[i])
				return false;
		return true;
	}
	
	public void incrementProcessTimestampByOne(int ID) {
//		Thread.sleep(100);
		ID = ID - 1;
		System.out.println("TimestampID: " + ID + ", Timestamp length: " + timevector.length + " value " + timevector[ID]);
		timevector[ID] = timevector[ID] + 1;
		System.out.println("this worked");
	}
	
	public void replaceWithTimestamp (Timestamp timestamp) {
		for (int i = 0; i < timevector.length; i++) {
			this.timevector[i] = timestamp.getTimevector()[i];
		}
	}
	
	public int[] getTimevector() {
		return timevector;
	}
	
	public String toString(){
		String text = "(";
		for(int i=0;i<timevector.length;i++) {
			text+=timevector[i] + ",";
		}
		text = text.substring(0, text.length()-1);
		text= text+")";
		return text;
	}
	
}