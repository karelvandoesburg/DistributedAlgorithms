package RandomizedByzantine;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class FPNothingworksClient extends Client {
	
	private String host = "rmi://127.0.0.1:1099";

	public static void main(String[] args) {
		Client client = new Client();
		new Thread(client).start();
	}
	
	@Override
	public void run() {
		try{
			ServerIF server = (ServerIF) Naming.lookup(this.host + "/server");
			FPNothingworks process = new FPNothingworks();
			process.setProcessID(server.getNextID());
			this.addProcessToRegistry(process, this.host);
			server.incrementProcesses();
		}
		catch(Exception e) {
			e.printStackTrace();
			this.run();
		}
	}
	
	public void addProcessToRegistry(Process process, String host) {
		int ID = process.getProcessID();
		try {
			Naming.bind(host + "/" + Integer.toString(ID), process);
			System.out.println("Process " + ID + " added in " + host + "/" + Integer.toString(ID));	
		}
		
		catch(Exception e) {
			System.out.println("Exception in addProcessToRegistry in Client: " + e);
			e.printStackTrace();
		}
	}

}
