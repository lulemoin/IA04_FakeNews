package gui;

import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;
import jade.core.AID;
import java.util.Random;



public class IndividuAgentMason implements Steppable {
	private Random random = new Random();
	boolean believer = random.nextBoolean();
	AID id;
	public Population beings;

	@Override
	public void step(SimState state) {
		
		// ce qu'il se passe à chaque step
		
		/*
		 * TODO: check if agent is a believer or not
		 */
		
		

	}
	
	
}
