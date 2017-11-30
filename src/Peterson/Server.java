package Peterson;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import BirmanSchiperStephenson.IFProcess;

public class Server {

	private String host;
	private ArrayList<Integer> components;
	
	public Server(String host) {
		this.host = host;
		this.components = new ArrayList<Integer>();
	}
	
	public void startElection() {
		this.executeElectionRound();
	}
	
	public synchronized void executeElectionRound() {
		this.passValuesToNeighbours();
	}
	
	public synchronized void passValuesToNeighbours() {
		this.passLeftValuesToNeighbours();
		this.passMaxValuesToNeighbours();
		this.checkActivity();
	}
	
	public synchronized void passLeftValuesToNeighbours() {
		for(Integer componentID: components) {
			ComponentIF component = (ComponentIF) this.getComponentFromServer(componentID, host);
			try {
				if(component.isActive()) {
					int rightID = component.getRightID();
					ComponentIF rightneighbour = (ComponentIF) this.getComponentFromServer(rightID, host);
					while(!rightneighbour.isActive()) {
						rightID = rightneighbour.getRightID();
						rightneighbour = (ComponentIF) this.getComponentFromServer(rightID, host);
					}
					rightneighbour.setLeftID(component.getComponentID());
				}
				
			} 
			catch (RemoteException e) {
				System.out.println("Exception is the executeElectionRound: " + e);
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void passMaxValuesToNeighbours() {
		for(Integer componentID: components) {
			ComponentIF component = (ComponentIF) this.getComponentFromServer(componentID, host);
			try {
				if(component.isActive()) {
					int rightID = component.getRightID();
					ComponentIF rightneighbour = (ComponentIF) this.getComponentFromServer(rightID, host);
					while(!rightneighbour.isActive()) {
						rightID = rightneighbour.getRightID();
						rightneighbour = (ComponentIF) this.getComponentFromServer(rightID, host);
					}
					rightneighbour.setMaxID(Math.max(component.getComponentID(), component.getLeftID()));
				}
				
			} 
			catch (RemoteException e) {
				System.out.println("Exception is the executeElectionRound: " + e);
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void checkActivity() {
		for(Integer componentID: components) {
			ComponentIF component = (ComponentIF) this.getComponentFromServer(componentID, host);
			try {
				if(component.isActive()) {
					if((component.getLeftID() < component.getComponentID()) && (component.getLeftID() < component.getComponentID())) {
						component.setComponentToRelay();
					}
				}
				
			} 
			catch (RemoteException e) {
				System.out.println("Exception is the executeElectionRound: " + e);
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void addComponentToServer(Component component) {
		this.bindComponentInCircle(component);
	}
	
	public void bindComponentInCircle(Component component) {
		if(components.isEmpty()) {
			components.add(component.getComponentID());
			addProcessToRegistry(component);
		}
		else {
			int getter = Calculate.createRandomNumberBetween(0, components.size()-1);
			int neighbourID = this.components.get(getter);
			ComponentIF neighbour = (ComponentIF) this.getComponentFromServer(neighbourID, host);
			try {
				int rightID = neighbour.getRightID();
				neighbour.setRightID(component.getComponentID());
				component.setRightID(rightID);
				component.setLeftID(neighbour.getComponentID());
				this.components.add(component.getComponentID());
				ComponentIF neighbour2 = (ComponentIF) this.getComponentFromServer(rightID, host);
				neighbour2.setLeftID(component.getComponentID());
				addProcessToRegistry(component);
			} catch (RemoteException e) {
				System.out.println("Exception in bindComponentInCirle in server: "+ e);
				e.printStackTrace();
			}
		}
	}
	
	public static ComponentIF getComponentFromServer(int componentID, String host) {
		try{
			String id = Integer.toString(componentID);
			ComponentIF component = (ComponentIF) Naming.lookup(host + "/" + id);
			return component;
		}
		catch(Exception e) {
			System.out.println("Exception in getProcessFromRegistry in Server: " + e);
			e.printStackTrace();
		}
		return null;
	}
	
	public void addProcessToRegistry(Component component) {
		try {
			Naming.bind(host + "/" + Integer.toString(component.getComponentID()), component);
			System.out.println("Component " + component.getComponentID() + " added in " + host + "/" + Integer.toString(component.getComponentID()));	
		}
		
		catch(Exception e) {
			System.out.println("Exception in addProcessToRegistry in Client: " + e);
			e.printStackTrace();
		}
	}
	
}
