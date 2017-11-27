package BirmanSchiperStephenson;

import java.time.Instant;

public class BufferUnit {

	private long time;
	
	public BufferUnit(Message message, long time) {
		this.time = time;
		long now = Instant.now().toEpochMilli();
	}

	
}
