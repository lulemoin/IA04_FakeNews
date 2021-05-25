package main;


import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import java.util.HashMap;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import Agents.IndividuAgent;

import model.Constants;

public class SecondaryBoot {

	public static String SECONDARY_PROPERTIES_FILE = "properties/second.properties";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		startWithProfile();
	}

	public static void startWithProfile() {
		Runtime rt = Runtime.instance();
		ProfileImpl p = null;
		ContainerController cc;
		try {
			p = new ProfileImpl(SECONDARY_PROPERTIES_FILE);
			cc = rt.createAgentContainer(p);
			AgentController ac;
			
			//HashMap<AID, Double> connexions = new HashMap<AID, Double>(); 
						

			
			for (int i = 1 ; i <= Constants.NOMBRE_INDIVIDUS ; i++) {
				
				ac = cc.createNewAgent("Individu", "Agents.IndividuAgent", null);
				ac.start();
			}

			ac = cc.createNewAgent("Demandeur", "Agents.DemandeurAgent", null);
			ac.start();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
