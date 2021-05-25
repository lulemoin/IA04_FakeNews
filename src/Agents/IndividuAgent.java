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
	boolean connexions_set = false;
	
	/* SOIT ON FAIT UNE CLASSE CONNEXION AVEC aid1, aid2 et intensite de la co SOIT DIRECTEMENT DANS UNE LISTE DANS INDIVIDU AGENT */
	HashMap<AID, Double> connexions = new HashMap<AID, Double>();
	
	
	protected void setup() {
		System.out.println(getLocalName() + "--> Installed");
		
		//r√©cup√©ration des objets pass√©s en param√®tres
		Object[] args = getArguments();
		this.esprit_critique = (double)args[0];
		this.degre_communication = (double)args[1];
		this.connexions = (HashMap<AID, Double>)args[2];
		
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
	
	/* Behaviour qui attend les requ√™tes de news de l'agent Environnement */
	public class WaitforRequestBehaviour extends CyclicBehaviour {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		
		@Override
		public void action() {
			ACLMessage message = receive(mt);
			if (message != null) {
				// CrÈer la news et la partage ‡ ses connexions sous la forme d'un message de type PROPAGATE
				
				
				
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
				// CrÈer la news et la partage ‡ ses connexions sous la forme d'un message de type PROPAGATE
				
				
				
			} else
				block();
		}
	}
	
}
