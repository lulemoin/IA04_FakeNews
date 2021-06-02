package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.proto.AchieveREInitiator;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.lang.Double;

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
	private News news_instance;
	
	//Double c'est l'intensite de la connexion
	HashMap<AID, Double> connexions = new HashMap<AID, Double>();
	
	protected void setup() {
		System.out.println(getLocalName() + "--> Installed");
		
		
		Random r = new Random();
		while (esprit_critique <0 && esprit_critique >0)
			esprit_critique = Math.round(r.nextGaussian()) * Constants.ECART_TYPE_ESPRIT_CRITIQUE + Constants.MOYENNE_ESPRIT_CRITIQUE;

		while (degre_communication <0 && degre_communication >0)
			degre_communication = Math.round(r.nextGaussian()) * Constants.ECART_TYPE_DEGRE_COMMUNICATION + Constants.MOYENNE_DEGRE_COMMUNICATION;		
		
		
		addBehaviour(new subscriptionBehaviour());
		addBehaviour(new SetupConnexionsBehaviour());
		addBehaviour(new WaitforRequestBehaviour());
		addBehaviour(new WaitforNewsFromConnexions());
	}
	
	public class subscriptionBehaviour extends Behaviour {
		public void action() {
			if(true) {//if connexions_set quand ce sera fait
				ACLMessage sub = new ACLMessage(ACLMessage.SUBSCRIBE);
				sub.addReceiver(new AID(Constants.DEFAULT_DEMANDEUR_AGENT, AID.ISLOCALNAME));
				System.out.printf("demande d'ajout dans la liste \n");
				send(sub);
			}
		}

		public boolean done() {
			return true;
		}
	}
	
	public class SetupConnexionsBehaviour extends Behaviour {
		
		public void action() {
			ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			if (msg != null) {
				System.out.print("Message : " + msg + "\n");
				try {
					//System.out.print(msg.getContentObject() + "\n");
					HashMap<AID, Double> connexions_string = (HashMap<AID, String>) msg.getContentObject();
					System.out.print(connexions_string + "\n");
					for (HashMap.Entry<AID, String> co : connexions_string.entrySet()) {
						System.out.print("for...\n");
						//connexions.put(co.getKey(), Double.valueOf(co.getValue()));
				    }
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
				connexions_set = true;
				System.out.print("Connections : " + connexions + "\n");
			}
			else {
				block();
			}
		}

		@Override
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
				News news = News.getInstance();	
				news.setEmetteurInitial(myAgent.getAID());
			
				for (AID id : connexions.keySet()) {
					ACLMessage partage = new ACLMessage(ACLMessage.PROPAGATE);
					partage.addReceiver(id);
					partage.setContent(String.valueOf(connexions.get(id)));
					send(partage);
				}
				
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
				
				addBehaviour(new DecisionBehaviour(myAgent, message));
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
			
			News news_transmettre = News.getInstance();
			double croire;
			double partage;
			
			/*
			Vr,In,Ec,Dc,Ic  app [0;1]
					F(Vr, In, np,Ec,Dc,Ic)=coeff *In * coeff(Vr^2)* coeff(1/Ec)* Dc *Ic* coeff*np
					ou fonction 2 parties : 
					I believe :
					f(V,I,Ec)=(coeff *In * coeff(Vr^2)* coeff(1/Ec))^i
					I share :
					G(f,np,Dc,Ic)=np/10^i * coeff *Dc* 1[Ic>0,8 et Dc> ?]
			*/
			double Vr = news_transmettre.getVeracite();
			double In = news_transmettre.getIntensite();
			int Np = news_transmettre.getNpartage();
			int IntConnexion = Integer.parseInt(message.getContent()); 
			boolean news_proche = connexions.containsKey(news_transmettre.getEmetteurInitial());
			System.out.println("\n News proche ?" + news_proche + "\n");
			
			System.out.println(esprit_critique + degre_communication + "\n");
			
			// a réviser, ce n'est qu'un exemple 
			// en utilisant la proximité de la news
			// utiliser le IntConnexion
			croire = In * Vr * Vr * (1/esprit_critique) * IntConnexion;
			croire = news_proche? 1.3 * croire : croire;
			if (croire > 1) {
				croire = 1;
			}
			/* 
			 * AJOUTER Intensité connexion Ic : recupérer le Double du HashMap
			 * */
			
			if(croire>0.75) {
				news_transmettre.incrementeNatteints();
			}
			
			partage=croire * Np/Constants.NOMBRE_INDIVIDUS * degre_communication;
			if(partage>0.5) {
				// nécessaire si plusieurs news simultanément
				//news.add(news_transmettre);
				news_transmettre.incrementeNpartage();
				
				for (AID id : connexions.keySet()) {
					ACLMessage propagate = new ACLMessage(ACLMessage.PROPAGATE);
					propagate.addReceiver(id);
					//partage.setContent(message.getContent());
					propagate.setContent(String.valueOf(connexions.get(id)));
					// newsTransmise.setContent(news.toJSON());
					send(propagate);
				}

				
				
			}
		
				
			}
		
		}
	
}
