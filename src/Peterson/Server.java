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
		int elected = Integer.MIN_VALUE;
		while(elected == Integer.MIN_VALUE) {
			elected = this.executeElectionRound();
			System.out.println("");
			System.out.println("");
		}
		System.out.println("The elected component, is component: " + elected);
	}
	
	public int executeElectionRound() {
		this.passValuesToNeighbours("tid");
		int elected = this.checkIfElected();
		if(elected != Integer.MIN_VALUE) {
			return elected;
		}
		this.passValuesToNeighbours("nntid");
		elected = this.checkIfElected();
		if(elected != Integer.MIN_VALUE) {
			return elected;
		}
		this.checkActivity();
		return Integer.MIN_VALUE;
	}
	
	public void passValuesToNeighbours(String value) {
		for(Integer componentID: components) {
			ComponentIF component = (ComponentIF) Server.getComponentFromServer(componentID, host);
			try {
				if(component.isActive()) {
					int rightID = component.getRightID();
					ComponentIF rightneighbour = (ComponentIF) Server.getComponentFromServer(rightID, host);
					while(!rightneighbour.isActive()) {
						this.passTidValueToNeighbour(component, rightneighbour);
						rightID = rightneighbour.getRightID();
						rightneighbour = (ComponentIF) Server.getComponentFromServer(rightID, host);
					}
					if(value == "tid") {this.passTidValueToNeighbour(component,rightneighbour);}
					else {this.passNNtidValueToNeighbour(component,rightneighbour);}
				}
				
			} 
			catch (RemoteException e) {
				System.out.println("Exception is the executeElectionRound: " + e);
				e.printStackTrace();
			}
		}
	}
	
	public void passTidValueToNeighbour(ComponentIF component, ComponentIF rightneighbour) {
		try {
			rightneighbour.setNtid(component.getTid());
			System.out.println("passing the tid value");
			System.out.println(rightneighbour.componentToString());
		} 
		catch (RemoteException e) {
			System.out.println("Exception in passNNtidValueToNeighbour in Server: " + e);
			e.printStackTrace();
		}
	}
		
	public void passNNtidValueToNeighbour(ComponentIF component, ComponentIF rightneighbour) {
		try {
				int nntid = Math.max(component.getTid(), component.getNtid());
				rightneighbour.setNNtid(nntid);
				System.out.println("passing the NNtid value");
				System.out.println(rightneighbour.componentToString());
			} 
		catch (RemoteException e) {
			System.out.println("Exception in passTidValueToNeighbour in Server: " + e);
			e.printStackTrace();
		}
	}
	
	public int checkIfElected() {
		for(Integer componentID: components) {
			ComponentIF component = (ComponentIF) Server.getComponentFromServer(componentID, host);
			try {
				if (component.isActive()) {
					if((component.getNtid() == component.getComponentID()) || component.getNNtid() == component.getComponentID()) {
						return component.getComponentID();
					}
				}
				else {
					if(component.getNtid() == component.getComponentID()) {
						return component.getComponentID();
					}
				}
			} 
			catch (RemoteException e) {
				System.out.println("Exception is the executeElectionRound: " + e);
				e.printStackTrace();
			}
		}
		return Integer.MIN_VALUE;
	}
	
	public void checkActivity() {
		for(Integer componentID: components) {
			ComponentIF component = (ComponentIF) Server.getComponentFromServer(componentID, host);
			try {
				if(component.isActive()) {
					if((component.getNtid() >= component.getTid()) && component.getNtid() >= component.getNNtid()) {
						component.setTid(component.getNtid());
					}
					else {
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
				this.components.add(component.getComponentID());
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
