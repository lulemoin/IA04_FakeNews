package main;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;

public class MainBoot {
	public static String MAIN_PROPERTIES_FILE = "properties/main.properties";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boot_gui();
	}

	public static void boot_gui() {
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
