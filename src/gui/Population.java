package gui;

import sim.engine.*;
import sim.util.*;
import sim.field.continuous.*;
import sim.field.network.*;

import model.Constants;
import model.News;

import java.util.HashMap;
import java.util.List;

import Agents.DemandeurAgent;


import gui.InstantSimOverview;


public class Population extends SimState{
	public Continuous2D yard = new Continuous2D(1.0,Constants.WINDOW_SIZE,Constants.WINDOW_SIZE);
	double forceToSchoolMultiplier = 0.01;
	double randomMultiplier = 0.1;
	//network creation - true is because it is a directed graph
	public Network buddies = new Network(true);
	
	
	// To link Individu object with their id and facilitate iteration over them
	public HashMap<Integer, IndividuAgentMason> agents;
	
	double middleY = yard.getHeight() * 0.5;
	double middleX = yard.getWidth() * 0.5;

	
	
	public Population(long seed)
	{
		super(seed);
		
		InstantSimOverview simOverview = InstantSimOverview.getInstance();
		// simOverview.clear();
		
		simOverview.addPropertyChangeListenerBelieverList(Constants.BELIEVER_CHANGE, evt -> {
			Object[] vals = (Object[]) evt.getNewValue();
			int id = (int) vals[0];
			boolean bool = (boolean) vals[1];
			IndividuAgentMason ind = agents.get(id);
			ind.believer = bool;
			
		});
		
		simOverview.addPropertyChangeListenerReadNews(Constants.READ_NEWS, evt -> {
			Object[] vals = (Object[]) evt.getNewValue();
			int id = (int) vals[0];
			boolean bool = (boolean) vals[1];
			IndividuAgentMason ind = agents.get(id);
			ind.reached = bool;
		});
		
	}
	public void start()
	{
		super.start();
		// clear the yard
		yard.clear();
		// clear the buddies
		buddies.clear();
		// add some students to the yard
		agents = new HashMap<Integer, IndividuAgentMason>();

		addAgents();

		schedule();
		
		
		
		/* Ajout des connexions (� finir/ v�rifier)
		
		InstantSimOverview ISO = InstantSimOverview.getInstance();
		for (String aid : ISO.getIndividuAgents()) {
			for (HashMap.Entry<String, Double> entry : ISO.getConnexion(aid).entrySet()) {
			    buddies.addEdge(aid, entry.getKey(), entry.getValue());
			}
			
			
			
		
		 * TO GET all mason object : 
		 * 	''' Bag students = buddies.getAllNodes(); '''
		*/
				
	}
	
	private void schedule() {
		agents.forEach((k, v) -> {
				schedule.scheduleRepeating(v);
			});
	}
	
	private void addAgents() {
		for(int i = 1; i <= Constants.NOMBRE_INDIVIDUS; i++)
		{
			IndividuAgentMason individu = new IndividuAgentMason(i);
			int pos1 = 1;
			int pos2 = 1;
			if(random.nextBoolean()) {
				pos1 = -1;
			}
			if(random.nextBoolean()) {
				pos2 = -1;
			}
			yard.setObjectLocation(
				individu,
				new Double2D(
						middleX + random.nextDouble() * middleX * 0.85 * pos1,
						middleY + random.nextDouble() * middleY * 0.85 * pos2
				)
			);
			buddies.addNode(individu);
			agents.put(i, individu);
		}
	}
	
	
}
