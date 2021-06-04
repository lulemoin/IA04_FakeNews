package main;

import java.util.ArrayList;
import java.util.Collections;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import java.util.HashMap;
import java.util.Random;

import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import Agents.IndividuAgent;
import gui.Population;
import gui.PopulationWithUI;
import model.Constants;
import model.News;
import sim.display.Console;

public class SecondaryBoot {

	public static String SECONDARY_PROPERTIES_FILE = "properties/second.properties";
	private static ArrayList<String> noms_individus = new ArrayList<String>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		startWithProfile();
		
		// -------------- News singleton creation ---------------		
		
		
		// veracite demandée à l'utilisateur et intensité en découle
		Random random1 = new Random();
		double veracite = random1.nextDouble();
		if (veracite == 0) {
			veracite = 0.01;
		}
		Random random2 = new Random();
		System.out.println("veracite =  " + veracite);
		double intensite;
		//veracite entre 0,8 et 0,1, l'intensite est entre 0,3 et 1
		if (veracite > 0.8) {
			 intensite = 0.3 + random2.nextInt(70)/100;
		}
		// veracite entre 0,4 et 0,8, l'intensite est entre 0,5 et 1
		else if (veracite > 0.4) {
			 intensite = 0.5 + random2.nextInt(50)/100;
		}
		//veracite entre 0 et 0,4, l'intensite est entre 0,7 et 1
		else{
			 intensite = 0.7 + random2.nextInt(30)/100;
		}
		System.out.println("intensite = " + intensite);
		News news_instance = News.getInstance();
		news_instance.setVeracite(veracite);
		news_instance.setIntensite(intensite);
		
		// To coment/decomment if you want GUI or not.
		
		//runUI();
		
	}

	public static void startWithProfile() {
		Runtime rt = Runtime.instance();
		ProfileImpl p = null;
		ContainerController cc;
		try {
			p = new ProfileImpl(SECONDARY_PROPERTIES_FILE);
			cc = rt.createAgentContainer(p);
			AgentController ac;
			
			ac = cc.createNewAgent("Demandeur", "Agents.DemandeurAgent", null);
			ac.start();

			// initialisation agents
			for (int i = 1 ; i <= Constants.NOMBRE_INDIVIDUS ; i++) {
				String name_agent = "Individu"+ i;
				ac = cc.createNewAgent(name_agent, "Agents.IndividuAgent", null);
				ac.start();
				noms_individus.add(name_agent);
			}


		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void runUI() {
		Population model = new Population(System.currentTimeMillis());
		PopulationWithUI gui = new PopulationWithUI(model);
		Console console = new Console(gui);
		console.setVisible(true);
		console.pressPlay();
	}
}
