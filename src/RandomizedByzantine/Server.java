package RandomizedByzantine;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements ServerIF{

	private String host;
	private static final long serialVersionUID = 1L;
	private int amountofprocesses;
	
	protected Server(String host) throws RemoteException {
		super();
		this.host = host;
		this.amountofprocesses = 0;
	}
	
	@Override
	public void addProcessToServer(Process process) {
		try {
			process.setProcessID(amountofprocesses);
			Naming.bind(this.host + "/" + Integer.toString(process.getProcessID()), process);
			System.out.println("Process " + process.getProcessID() + " added in " + host + "/" + Integer.toString(process.getProcessID()));
			amountofprocesses++;
		}
		
		catch(Exception e) {
			System.out.println("Exception in addProcessToRegistry in Client: " + e);
			e.printStackTrace();
		}
	}
	
	@Override
	public int getNextID() {
		return this.amountofprocesses;
	}

	@Override
	public void incrementProcesses() throws RemoteException {
		this.amountofprocesses++;
	}

}
