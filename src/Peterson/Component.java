package Peterson;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Component extends UnicastRemoteObject implements ComponentIF, Runnable{
	
	public int componentID;
	public int rightID;
	public int tid;
	public int ntid;
	public int nntid;
	private boolean isactive;
	private String host = "rmi://127.0.0.1:1099";
	
	public Component(int componentID) throws RemoteException {
		super();
		this.tid = componentID;
		this.isactive = true;
		this.ntid = Integer.MAX_VALUE;
		this.nntid = Integer.MAX_VALUE;
	}
	
	@Override
	public void run() {
		try {
			ServerIF server = (ServerIF) Naming.lookup(host + "/server");
//			int componentID = server.getComponents().size();
//			this.componentID = componentID;
			this.bindComponentInCircle(server);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int getComponentID() {
		return this.componentID;
	}

	@Override
	public int getRightID() {
		return this.rightID;
	}

	@Override
	public void setRightID(int rightID) {
		this.rightID = rightID;		
	}

	@Override
	public boolean isActive() throws RemoteException {
		return this.isactive;
	}

	@Override
	public void setComponentToRelay() throws RemoteException {
		this.isactive = false;
	}

	@Override
	public int getTid() throws RemoteException {
		return this.tid;
	}

	@Override
	public int getNtid() throws RemoteException {
		return this.ntid;
	}

	@Override
	public int getNNtid() throws RemoteException {
		return this.nntid;
	}

	@Override
	public void setTid(int tid) throws RemoteException {
		this.tid = tid;
	}

	@Override
	public void setNtid(int ntid) throws RemoteException {
		this.ntid = ntid;
	}

	@Override
	public void setNNtid(int nntid) throws RemoteException {
		this.nntid = nntid;
	}

	@Override
	public String componentToString() throws RemoteException {
		return "Component: " + this.componentID  + ", tid: " + this.tid + ", ntid: " + this.ntid + ", nntid: " + this.nntid + ", active: " + this.isactive;
	}
	
	public void bindComponentInCircle(ServerIF server) throws RemoteException {
		ArrayList<Integer> components = server.getComponents();
		if(components.isEmpty()) {
			server.addComponent(this.componentID);
			Main.addComponentToRegistry(this, host);
		}
		else {
			int getter = Calculate.createRandomNumberBetween(0, components.size()-1);
			int neighbourID = components.get(getter);
			ComponentIF neighbour = (ComponentIF) Server.getComponentFromServer(neighbourID, host);
			try {
				int rightID = neighbour.getRightID();
				neighbour.setRightID(this.componentID);
				this.setRightID(rightID);
				server.addComponent(this.componentID);
				Main.addComponentToRegistry(this, host);
			} catch (RemoteException e) {
				System.out.println("Exception in bindComponentInCirle in server: "+ e);
				e.printStackTrace();
			}
		}
	}

}
