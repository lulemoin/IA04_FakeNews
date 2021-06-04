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
		
		generateNews();
		
		// To coment/decomment to disable/enable GUI
		runUI();
		
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
	
	public static void generateNews() {
		// veracite demandee a  l'utilisateur et intensite en decoule
		Random random1 = new Random();
		double veracite = random1.nextDouble();
		if (veracite == 0) {
			veracite = 0.01;
		}
		Random random2 = new Random();
		System.out.println("Veracite =  " + veracite + "\n");
		
		double intensite;
		if (veracite > 0.8) { //  0.8 < veracite < 1 => 0.3 < intensite < 1
			 intensite = 0.3 + random2.nextInt(70)/100;
		}
		else if (veracite > 0.4) { //  0,4 < veracite < 0,8 => 0.5 < intensite < 1
			 intensite = 0.5 + random2.nextInt(50)/100;
		}
		else { //  0 < veracite < 0,4 => 0.7 < intensite < 1
			 intensite = 0.7 + random2.nextInt(30)/100;
		}
		
		System.out.println("Intensite = " + intensite + "\n");
		News news_instance = News.getInstance();
		news_instance.setVeracite(veracite);
		news_instance.setIntensite(intensite);
	}
	
	
	public static void runUI() {
		Population model = new Population(System.currentTimeMillis());
		PopulationWithUI gui = new PopulationWithUI(model);
		Console console = new Console(gui);
		console.setVisible(true);
		console.pressPlay();
	}
}
