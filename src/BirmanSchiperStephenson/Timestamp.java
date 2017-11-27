package BirmanSchiperStephenson;

public class Timestamp {
	private int[] timevector;
	
	public Timestamp(int newVector){
		timevector = new int[newVector];
		for(int i = 0; i < newVector; i++) {
			timevector[i] = 0;
		}
	}
	
	public boolean isLargerOrEqualToTimestamp (Timestamp SendingProcess, Timestamp ReceivingProcess) {
		for (int i=0; i<SendingProcess.timevector.length;i++)
			if (SendingProcess.timevector[i]<=ReceivingProcess.timevector[i])
				return false;
		return true;
	}
	
	public void incrementProcessTimestampByOne(int ID) {
		ID = ID - 1;
		timevector[ID] = timevector[ID] + 1;
	}
	
	public void updateTimestamp (Timestamp timestamp) {
		this.timevector = timestamp.getTimevector();
	}
	
	public int[] getTimevector() {
		return timevector;
	}
	
	public String GoToString(){
		String text = "Vector is (";
		for(int i=0;i<timevector.length;i++) {
			text+=timevector[i] + ",";
		}
		text = text.substring(0, text.length()-1);
		text= text+")";
		return text;
	}
	
}