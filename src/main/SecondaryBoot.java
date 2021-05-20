package main;


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
			
			HashMap<String, Double> connexions = new HashMap<String, Double>(); 
			
			for (int i = 1 ; i <= Constants.NOMBRE_INDIVIDUS ; i++) {
				connexions.put("Individu2", 0.50);
				connexions.put("Individus3", 0.60);
				ac = cc.createNewAgent("Individu", "Agents.IndividuAgent", 
						new Object [] {0.5, 0.5, connexions});
				ac.start();
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
