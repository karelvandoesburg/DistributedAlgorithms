package BirmanSchiperStephenson;

import java.time.Instant;

public class Calculate {

	public static int createRandomNumberBetween(int smallest, int largest) {
		return (int )(Math.random() * ((largest+1)-smallest) + smallest);
	}
	
}
