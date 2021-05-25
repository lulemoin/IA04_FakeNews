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

import java.util.Random;
import model.Constants;


import model.News;

public class IndividuAgent extends Agent{
	double esprit_critique = -1;
	double degre_communication = -1;
	List<News> news = new ArrayList();
	
	/* SOIT ON FAIT UNE CLASSE CONNEXION AVEC aid1, aid2 et intensite de la co SOIT DIRECTEMENT DANS UNE LISTE DANS INDIVIDU AGENT */
	HashMap<String, Double> connexions = new HashMap<String, Double>();

	protected void setup() {
		System.out.println(getLocalName() + "--> Installed");
		
		//rÃ©cupÃ©ration des objets passÃ©s en paramÃ¨tres
		//TO-DO : a suppr
		Object[] args = getArguments();
		this.esprit_critique = (double)args[0];
		this.degre_communication = (double)args[1];
		this.connexions = (HashMap<String, Double>)args[2];
		
		// Initialisation des paramètres suivant une loi normale
		
		//TO-DO : à couper-coller dans la fonction setup_connexions (qui sera executé quand tous les agents seront intialisés)
		Random r = new Random();
		
		int nb_connexions = (int) Math.round(r.nextGaussian()) * Constants.ECART_TYPE_NB_CONNEXION + Constants.MOYENNE_NB_CONNEXION ;
		
		
		while (esprit_critique <0 && esprit_critique >0)
			esprit_critique = Math.round(r.nextGaussian()) * Constants.ECART_TYPE_ESPRIT_CRITIQUE + Constants.MOYENNE_ESPRIT_CRITIQUE;
		
		while (degre_communication <0 && degre_communication >0)
			degre_communication = Math.round(r.nextGaussian()) * Constants.ECART_TYPE_DEGRE_COMMUNICATION + Constants.MOYENNE_DEGRE_COMMUNICATION;		
		
		//addBehaviour(new WaitforRequestBehaviour());
	}
	
	/* Behaviour qui attend les requÃªtes de news de l'agent Environnement */
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
