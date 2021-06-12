package main;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import Agents.IndividuAgent;
import gui.Population;
import gui.PopulationWithUI;
import model.Constants;
import model.CsvFile;
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
		
		News.generateNews();
		
		// To coment/decomment to disable/enable GUI
		runUI();
		
	}

	public static void startWithProfile() {
		CsvFile file= CsvFile.getInstance(); 
		file.setFile();
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
