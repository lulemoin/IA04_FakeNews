package main;


import java.util.Random;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import model.News;

public class MainBoot {
	public static String MAIN_PROPERTIES_FILE = "properties/main.properties";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boot_gui();
		
		
		
		// -------------- News singleton creation ---------------		
		
		// A modifier pour corréler veracité et intensité
		Random random1 = new Random();
		double veracite = random1.nextDouble();
		Random random2 = new Random();
		double intensite = random2.nextDouble();
		
		News news_instance = News.getInstance();
		

	}

	public static void boot_gui() {
		// open main console gui
		// properties: main=true; gui = true;
		Runtime rt = Runtime.instance();
		Profile p = null;
		try {
			p = new ProfileImpl(MAIN_PROPERTIES_FILE);
			rt.createMainContainer(p);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
