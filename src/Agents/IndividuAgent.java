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

import E1.CafeAgent.RechargeBehaviour;
import model.Constants;


import model.News;

public class IndividuAgent extends Agent{
	double esprit_critique = -1;
	double degre_communication = -1;
	List<News> news = new ArrayList();
	boolean connexions_set = false;
	
	HashMap<AID, Double> connexions = new HashMap<AID, Double>();
	
	protected void setup() {
		System.out.println(getLocalName() + "--> Installed");
		
		//r√©cup√©ration des objets pass√©s en param√®tres
		//TO-DO : a suppr
		//Object[] args = getArguments();
		//this.esprit_critique = (double)args[0];
		//this.degre_communication = (double)args[1];
		//this.connexions = (HashMap<AID, Double>)args[2];
		//this.connexions = (HashMap<String, Double>)args[2];

//		Object[] args = getArguments();
//		this.esprit_critique = (double)args[0];
//		this.degre_communication = (double)args[1];
//		this.connexions = (HashMap<String, Double>)args[2];

		
		// Initialisation des paramËtres suivant une loi normale
		
		//TO-DO : ‡ couper-coller dans la fonction setup_connexions (qui sera executÈ quand tous les agents seront intialisÈs)
		Random r = new Random();
		
		int nb_connexions = (int) Math.round(r.nextGaussian()) * Constants.ECART_TYPE_NB_CONNEXION + Constants.MOYENNE_NB_CONNEXION ;
		
		while (esprit_critique < 0 && esprit_critique > 1)
			esprit_critique = Math.round(r.nextGaussian()) * Constants.ECART_TYPE_ESPRIT_CRITIQUE + Constants.MOYENNE_ESPRIT_CRITIQUE;
		
		while (degre_communication < 0 && degre_communication > 1)
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
	
	/* Behaviour qui attend les requetes de news de l'agent Environnement */
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
				
				addBehaviour(new DecisionBehaviour())
			} else
				block();
		}
	}
	
	public class DecisionBehaviour extends OneShotBehaviour {
		ACLMessage message;
		
		public DecisionBehaviour(Agent a, ACLMessage message) {
			super(a);
			this.message = message;
		}
		
		public void action() {
			String contenuMessage = message.getContent();
			
			ACLMessage demande = new ACLMessage(ACLMessage.REQUEST);
			
			News news=new News(demande)
			news.veracite
			news.intensite
			news.emetteurInitial
			esprit_critique
			degre_communication
			
			if (DOSE>0) {
				DOSE--;
				//affirme
				ACLMessage reply = message.createReply();
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent(Integer.toString(DOSE));
				send(reply);
				//System.out.println("Number of DOSE" + DOSE);
				
				if (DOSE==0) {
					//refuse
					Random r = new Random();
					long delay = r.nextInt(5000) + 500;
					addBehaviour(new RechargeBehaviour(myAgent, delay));
					System.out.println("Recharge en cours - effectif dans " + delay + " millisec \n");
				}
			}
			else {
				ACLMessage reply = message.createReply();
				reply.setPerformative(ACLMessage.REFUSE);
				reply.setContent("pas ok ");
				send(reply);
			}
				
			}
		
		}
	
}
