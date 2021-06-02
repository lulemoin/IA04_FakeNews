package main;

import sim.display.Console;
import gui.PopulationWithUI;
import gui.Population;


public class MainGUI {
	public static void main(String[] args) {
		 runUI();
	}

	public static void runUI() {
		Population model = new Population(System.currentTimeMillis());
		PopulationWithUI gui = new PopulationWithUI(model);
		Console console = new Console(gui);
		console.setVisible(true);
	}

}
