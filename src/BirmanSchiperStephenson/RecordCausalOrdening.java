package BirmanSchiperStephenson;

public class RecordCausalOrdening {

	private int[] counter;
	
	public RecordCausalOrdening(int amountofprocesses) {
		this.counter = new int[amountofprocesses];
	}
	
	public String incrementCounter(Message message) {
		int sendingID = message.getSendingID() - 1;
		int messageID = message.getMessageID();
		if(counter[sendingID]+1 == messageID) {
			counter[sendingID] = counter[sendingID] + 1;
			return "the message ordening is causal";
		}
		else {
			System.out.println("hier gaat het fout 2");
			counter[sendingID-1] = Integer.MAX_VALUE;
			return "the message ordening is NOT causal";
		}
	}
	
}
