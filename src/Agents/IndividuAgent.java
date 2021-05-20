package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.proto.AchieveREInitiator;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.Optional;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import model.News;

public class IndividuAgent extends Agent{
	double esprit_critique ;
	double degre_communication ;
	List<News> news = new ArrayList();
	
	/* SOIT ON FAIT UNE CLASSE CONNEXION AVEC aid1, aid2 et intensite de la co SOIT DIRECTEMENT DANS UNE LISTE DANS INDIVIDU AGENT */
	HashMap<String, Double> connexions = new HashMap<String, Double>();

	protected void setup() {
		System.out.println(getLocalName() + "--> Installed");
		
		//récupération des objets passés en paramètres
		Object[] args = getArguments();
		this.esprit_critique = (double)args[0];
		this.degre_communication = (double)args[1];
		this.connexions = (HashMap<String, Double>)args[2];		
		
		//addBehaviour(new WaitforRequestBehaviour());
	}
	
	/* Behaviour qui attend les requêtes de news de l'agent Environnement */
	public class WaitforRequestBehaviour extends CyclicBehaviour {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		
		@Override
		public void action() {
			ACLMessage message = receive(mt);
			if (message != null) {	
				
			} else
				block();
		}
	}
	
}
