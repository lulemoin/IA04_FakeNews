package gui;

import sim.engine.*;
import sim.util.*;
import sim.field.continuous.*;
import sim.field.network.*;

import model.Constants;
import Agents.DemandeurAgent;


import gui.InstantSimOverview;


public class Population extends SimState{
	public Continuous2D yard = new Continuous2D(1.0,300,300);
	double forceToSchoolMultiplier = 0.01;
	double randomMultiplier = 0.1;
	//network creation - true is because it is a directed graph
	public Network buddies = new Network(true);
	
	double middleY = yard.getHeight() * 0.5;
	double middleX = yard.getWidth() * 0.5;

	
	
	public Population(long seed)
	{
		super(seed);
	}
	public void start()
	{
		super.start();
		// clear the yard
		yard.clear();
		// clear the buddies
		buddies.clear();
		// add some students to the yard
		
		//TODO faire un tableau d'AID pour pouvoir identifier les agents mason et les lier aux agents JADE ?
		
		
		for(int i = 0; i < Constants.NOMBRE_INDIVIDUS; i++)
		{
			IndividuAgentMason individu = new IndividuAgentMason();
			yard.setObjectLocation(
				individu,
				new Double2D(
						middleX + random.nextDouble() * middleX * 0.5,
						middleY + random.nextDouble() * middleY * 0.5
				)
			);
			buddies.addNode(individu);
			schedule.scheduleRepeating(individu);
		}
		
		/*
		 * TODO add connexions between mason objects
		 * 		get connexions object and iterate over it
		 * 			''' buddies.addEdge(student, studentB, new Double( -buddiness)); '''
		 * 		
		 * HELP : buddiness is the intensity of the connexion !
		 * 
		 * TO GET all mason object : 
		 * 	''' Bag students = buddies.getAllNodes(); '''
		*/
				
	}
	public static void main(String[] args)
	{
		doLoop(Population.class, args);
		System.exit(0);
	}
}
