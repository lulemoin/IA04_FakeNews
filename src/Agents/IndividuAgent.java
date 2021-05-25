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
	boolean connexions_set = false;
	
	/* SOIT ON FAIT UNE CLASSE CONNEXION AVEC aid1, aid2 et intensite de la co SOIT DIRECTEMENT DANS UNE LISTE DANS INDIVIDU AGENT */
	HashMap<AID, Double> connexions = new HashMap<AID, Double>();
	
	
	protected void setup() {
		System.out.println(getLocalName() + "--> Installed");
		
		//rÃ©cupÃ©ration des objets passÃ©s en paramÃ¨tres
		//TO-DO : a suppr
<<<<<<< HEAD
		//Object[] args = getArguments();
		//this.esprit_critique = (double)args[0];
		//this.degre_communication = (double)args[1];
		//this.connexions = (HashMap<AID, Double>)args[2];
		//this.connexions = (HashMap<String, Double>)args[2];
=======
		Object[] args = getArguments();
		this.esprit_critique = (double)args[0];
		this.degre_communication = (double)args[1];
		this.connexions = (HashMap<String, Double>)args[2];
>>>>>>> c8355550d9cd2e98a70a36102b45518408f156f4
		
		// Initialisation des paramètres suivant une loi normale
		
		//TO-DO : à couper-coller dans la fonction setup_connexions (qui sera executé quand tous les agents seront intialisés)
		Random r = new Random();
		
		int nb_connexions = (int) Math.round(r.nextGaussian()) * Constants.ECART_TYPE_NB_CONNEXION + Constants.MOYENNE_NB_CONNEXION ;
<<<<<<< HEAD
=======
		
		
		while (esprit_critique <0 && esprit_critique >0)
			esprit_critique = Math.round(r.nextGaussian()) * Constants.ECART_TYPE_ESPRIT_CRITIQUE + Constants.MOYENNE_ESPRIT_CRITIQUE;
		
		while (degre_communication <0 && degre_communication >0)
			degre_communication = Math.round(r.nextGaussian()) * Constants.ECART_TYPE_DEGRE_COMMUNICATION + Constants.MOYENNE_DEGRE_COMMUNICATION;		
>>>>>>> c8355550d9cd2e98a70a36102b45518408f156f4
		
		
		while (esprit_critique <0 && esprit_critique >0)
			esprit_critique = Math.round(r.nextGaussian()) * Constants.ECART_TYPE_ESPRIT_CRITIQUE + Constants.MOYENNE_ESPRIT_CRITIQUE;
		
		while (degre_communication <0 && degre_communication >0)
			degre_communication = Math.round(r.nextGaussian()) * Constants.ECART_TYPE_DEGRE_COMMUNICATION + Constants.MOYENNE_DEGRE_COMMUNICATION;		
		
		addBehaviour(new subscriptionBehaviour());
		addBehaviour(new WaitforRequestBehaviour());
		addBehaviour(new WaitforNewsFromConnexions());
	}
	
	public class subscriptionBehaviour extends Behaviour {
		public void action() {
			if(connexions_set) {
				ACLMessage sub = new ACLMessage(ACLMessage.SUBSCRIBE);
				sub.addReceiver(DemandeurAgent.getInstance().getAID());
				send(sub);
			}
		}

		public boolean done() {
			return connexions_set;
		}
	}
	
	/* Behaviour qui attend les requÃªtes de news de l'agent Environnement */
	public class WaitforRequestBehaviour extends CyclicBehaviour {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		
		@Override
		public void action() {
			ACLMessage message = receive(mt);
			if (message != null) {
				// Créer la news et la partage à ses connexions sous la forme d'un message de type PROPAGATE
				
				
				
			} else
				block();
		}
	}
	
	// Propagate quand l'agent recoit des messages de type ACLMessage.PROPAGATE
		// evaluer la news => individu y croit ou pas
		// Relaie ou non la news
	public class WaitforNewsFromConnexions extends CyclicBehaviour {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE);
		
		@Override
		public void action() {
			ACLMessage message = receive(mt);
			if (message != null) {
				// Créer la news et la partage à ses connexions sous la forme d'un message de type PROPAGATE
				
				
				
			} else
				block();
		}
	}
	
}
