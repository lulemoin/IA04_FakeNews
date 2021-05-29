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

import model.Constants;
import model.News;

public class SecondaryBoot {

	public static String SECONDARY_PROPERTIES_FILE = "properties/second.properties";
	private static ArrayList<String> noms_individus = new ArrayList<String>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		startWithProfile();
		
		// -------------- News singleton creation ---------------		
		
		// A modifier pour corréler veracité et intensité
		
		Random r = new Random();
		
		Random random1 = new Random();
		double veracite = random1.nextDouble();
		Random random2 = new Random();
		double intensite = random2.nextDouble();
		
		News news_instance = News.getInstance();
		
	}

	public static void startWithProfile() {
		Runtime rt = Runtime.instance();
		ProfileImpl p = null;
		ContainerController cc;
		try {
			p = new ProfileImpl(SECONDARY_PROPERTIES_FILE);
			cc = rt.createAgentContainer(p);
			AgentController ac;

			// initialisation agents
			for (int i = 1 ; i <= Constants.NOMBRE_INDIVIDUS ; i++) {
				ac = cc.createNewAgent("Individu"+ i, "Agents.IndividuAgent", null);
				ac.start();
				noms_individus.add("Individu"+ i);
			}
			
			// initialisation connections
			for (int i = 1 ; i <= Constants.NOMBRE_INDIVIDUS ; i++) {
				AID aid = new AID("Individu"+ i, AID.ISLOCALNAME);
				int nb_connections = -1;
				Random r = new Random();
				while (nb_connections < 0)
					nb_connections = (int) Math.round(r.nextGaussian()) * Constants.ECART_TYPE_NB_CONNEXION + Constants.MOYENNE_NB_CONNEXION ;
				ArrayList<String> random_individus = new ArrayList<String>();
				Collections.copy(noms_individus, random_individus);
				Collections.shuffle(random_individus);
				for (int j = 0 ; j < nb_connections ; i++) {
					String nom = random_individus.remove(0);
					double intensite = -1;
					while (intensite < 0 && intensite > 1)
						intensite = Math.round(r.nextGaussian()) * Constants.ECART_TYPE_INTENSITE_CONNEXION + Constants.MOYENNE_INTENSITE_CONNEXION;					
					// TO-DO addConnection()
					// addConnection(nom, intensite);
				}
			}

			ac = cc.createNewAgent("Demandeur", "Agents.DemandeurAgent", null);
			ac.start();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
