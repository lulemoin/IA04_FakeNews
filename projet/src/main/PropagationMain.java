package main;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class PropagationMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		startWithProfile();
	}

	public static void startWithProfile() {
		Runtime rt = Runtime.instance();
		Profile p = null;
		ContainerController cc;
		try {
			/**
			 * host : null value means use the default (i.e. localhost) port - is the port
			 * number. A negative value should be used for using the default port number.
			 * platformID - is the symbolic name of the platform, isMain : boolean
			 */

			p = new ProfileImpl(null, -1, "simulation_news", false);

			cc = rt.createAgentContainer(p);
//
//			AgentController ac = cc.createNewAgent("Machine", "td3.agents.MachineAgent", null);
//			ac.start();
//			ac = cc.createNewAgent("Client1", "td3.agents.ClientAgent", null);
//			ac.start();
//			ac = cc.createNewAgent("Client2", "td3.agents.ClientAgent", null);
//			ac.start();
//			ac = cc.createNewAgent("Client3", "td3.agents.ClientAgent", null);
//			ac.start();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
