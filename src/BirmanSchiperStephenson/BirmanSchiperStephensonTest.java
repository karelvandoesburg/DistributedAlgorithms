package BirmanSchiperStephenson;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

public class BirmanSchiperStephensonTest {

	public static void main(String[] args) throws RemoteException, InterruptedException {
		
		int port = 1099;
		String host = "rmi://localhost:" + port;
		Main.createLocalRegistry(port);
		
	}
	
	@Test
	public void test() {
		assertEquals(1,1);
	}

}
