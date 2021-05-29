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
		
		
		// veracite demandée à l'utilisateur et intensité en découle
		Random random1 = new Random();
		double veracite = random1.nextDouble();
		Random random2 = new Random();
		double intensite;
		//veracite entre 0,8 et 0,1, l'intensité est entre 0 et 1
		if (veracite > 0.8) {
			 intensite = random2.nextInt(100)/100;
		}
		// veracite entre 0,4 et 0,8, l'intensité est entre 0,3 et 1
		else if (veracite > 0.4) {
			 intensite = 0.3 + random2.nextInt(70)/100;
		}
		//veracite entre 0 et 0,4, l'intensité est entre 0,7 et 1
		else{
			 intensite = 0.7 + random2.nextInt(30)/100;
		}
		
		News news_instance = News.getInstance();
		news_instance.setVeracite(veracite);
		news_instance.setIntensite(intensite);
		
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
			
			// initialisation connections
//			for (int i = 1 ; i <= Constants.NOMBRE_INDIVIDUS ; i++) {
//				AID aid = new AID("Individu"+ i, AID.ISLOCALNAME);
//				int nb_connections = -1;
//				Random r = new Random();
//				while (nb_connections < 0)
//					nb_connections = (int) Math.round(r.nextGaussian()) * Constants.ECART_TYPE_NB_CONNEXION + Constants.MOYENNE_NB_CONNEXION ;
//				ArrayList<String> random_individus = new ArrayList<String>();
//				Collections.copy(noms_individus, random_individus);
//				Collections.shuffle(random_individus);
//				for (int j = 0 ; j < nb_connections ; i++) {
//					String nom = random_individus.remove(0);
//					double intensite = -1;
//					while (intensite < 0 && intensite > 1)
//						intensite = Math.round(r.nextGaussian()) * Constants.ECART_TYPE_INTENSITE_CONNEXION + Constants.MOYENNE_INTENSITE_CONNEXION;					
					// TO-DO addConnection()
					// addConnection(nom, intensite);
//				}
//			}

		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
