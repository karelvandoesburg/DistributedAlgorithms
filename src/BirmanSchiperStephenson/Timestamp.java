package BirmanSchiperStephenson;

public class Timestamp {
	private int[] TimeVector;
	
	public boolean isLargerOrEqualToTimestamp (Timestamp SendingProcess, Timestamp ReceivingProcess) {
		for (int i=0; i<SendingProcess.TimeVector.length;i++)
			if (SendingProcess.TimeVector[i]<=ReceivingProcess.TimeVector[i])
				return false;
		return true;
	}
	
	public void updateTimestamp (Timestamp timestamp) {
//		int[] newVector = new int[SendingProcess.TimeVector.length];
//		for(int i = 0; i < SendingProcess.TimeVector.length;i++){
//			newVector[i] = Math.max(SendingProcess.TimeVector[i], ReceivingProcess.TimeVector[i]);
//		}
		this.TimeVector = timestamp.getTimevector();
	}
		
	public Timestamp(int newVector){
		TimeVector = new int[newVector];
}
	public int[] getTimevector() {
		return TimeVector;
	}
	
	public String GoToString(){
		String text = "Vector is (";
		for(int i=0;i<TimeVector.length;i++) {
			text+=TimeVector[i] + ",";
		}
		text = text.substring(0, text.length()-1);
		text= text+")";
		return text;
	}
	
}

