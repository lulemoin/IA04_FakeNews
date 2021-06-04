package gui;

import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;
import jade.core.AID;
import java.util.Random;



public class IndividuAgentMason implements Steppable {
	boolean believer = false;
	int id;
	public Population beings;
	
	public IndividuAgentMason(int val){
		id = val;
	}

	@Override
	public void step(SimState state) {
		
		// ce qu'il se passe à chaque step
		
		/*
		 * TODO: check if agent is a believer or not
		 */
		
		

	}
	
	
}
