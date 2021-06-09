package gui;

import sim.engine.*;
import sim.util.*;
import sim.field.continuous.*;
import sim.field.network.*;

import model.Constants;
import model.News;

import java.util.ArrayList;
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
	private List<Buddy_array> buddy_array = new ArrayList<Buddy_array>();
	
	double middleY = yard.getHeight() * 0.5;
	double middleX = yard.getWidth() * 0.5;
	
	private class Buddy_array{
		int id1;
		int id2;
		double intensite;
		
		Buddy_array(int id_1, int id_2, double _intensite){
			id1 = id_1;
			id2 = id_2;
			intensite = _intensite;
		}
	}

	public Population(long seed)
	{
		super(seed);
		
		InstantSimOverview simOverview = InstantSimOverview.getInstance();
		// simOverview.clear();
		
		agents = new HashMap<Integer, IndividuAgentMason>();
		
		createAgents();
		
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
		
		simOverview.addPropertyChangeListenerConnexions(Constants.NEW_CONNEXION, evt -> {
			Object[] vals = (Object[]) evt.getNewValue();
			int id = (int) vals[0];
			int id2 = (int) vals[1];
			double intensite = (double) vals[2];
			Buddy_array temp = new Buddy_array(id, id2, intensite);
			buddy_array.add(temp);
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

		addAgents();
		
		for (int i = 0; i < buddy_array.size(); i++) {
			Buddy_array temp = buddy_array.get(i);
			IndividuAgentMason ind1 = agents.get(temp.id1);
			IndividuAgentMason ind2 = agents.get(temp.id2);
			buddies.addEdge(ind1, ind2, temp.intensite);
        }

		schedule();
				
	}
	
	private void schedule() {
		agents.forEach((k, v) -> {
				schedule.scheduleRepeating(v);
			});
	}
	
	private void addAgents() {
		for(int i = 1; i <= Constants.NOMBRE_INDIVIDUS; i++)
		{
			IndividuAgentMason individu = agents.get(i);
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
		}
	}
	
	private void createAgents() {
		for(int i = 1; i <= Constants.NOMBRE_INDIVIDUS; i++)
		{
			IndividuAgentMason individu = new IndividuAgentMason(i);
			agents.put(i, individu);
		}
	}
	
	
}
