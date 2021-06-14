package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.proto.AchieveREInitiator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import gui.InstantSimOverview;
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
	boolean contamine=false;
	boolean readNews = false;
	int id;
	InstantSimOverview simOverview;
	
	//Double c'est l'intensite de la connexion
	HashMap<String, Double> connexions = new HashMap<String, Double>();
	//List<Connexion> connexions = new ArrayList<Connexion>();
	
	protected void setup() {
		System.out.println(getLocalName() + "--> Installed");
		
		//Get int ID from local name - get singleton simOverview (Mason_Linkage)
		String[] strFinal = getLocalName().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
		id = Integer.parseInt(strFinal[1]);
		
		simOverview = InstantSimOverview.getInstance();
		
		Random r = new Random();
		while (esprit_critique <=0 || esprit_critique >1)
			esprit_critique = r.nextGaussian() * Constants.ECART_TYPE_ESPRIT_CRITIQUE + Constants.MOYENNE_ESPRIT_CRITIQUE;

		while (degre_communication <=0 || degre_communication >1)
			degre_communication = r.nextGaussian() * Constants.ECART_TYPE_DEGRE_COMMUNICATION + Constants.MOYENNE_DEGRE_COMMUNICATION;		
		
		System.out.println(" individu " + getLocalName() + "  esprit_critique = "  + esprit_critique + " et degre_communication = " + degre_communication);
		addBehaviour(new subscriptionBehaviour());
		addBehaviour(new SetupConnexionsBehaviour());
		addBehaviour(new WaitforRequestBehaviour());
		addBehaviour(new WaitforNewsFromConnexions());
		addBehaviour(new WaitforEndingNewsBehaviour());
	}
	
	public class subscriptionBehaviour extends OneShotBehaviour {
		public void action() {
			ACLMessage sub = new ACLMessage(ACLMessage.SUBSCRIBE);
			sub.addReceiver(new AID(Constants.DEFAULT_DEMANDEUR_AGENT, AID.ISLOCALNAME));
			send(sub);
		}
	}
	
	public class SetupConnexionsBehaviour extends Behaviour {
		
		public void action() {
			ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			if (msg != null) {
				//System.out.print("Message : " + msg + "\n");
					//System.out.print(msg.getContentObject() + "\n");
					//List<AID, Double> connexions = (HashMap<AID, Double>) msg.getContentObject();
					//String connexions_string = msg.getContent();
					
					
					try {
						HashMap<String, Double> connexions_intermediaire = (HashMap<String, Double>) msg.getContentObject();
						connexions = (HashMap<String, Double>) msg.getContentObject();
						//connexions_intermediaire = (HashMap<String, String>) msg.getContentObject();
						//System.out.println("connexion inter = " + connexions_intermediaire);
						//System.out.println("connexion = " + connexions);
						
						change_connexions_state();
						
						/*for (String indiv : connexions_intermediaire.keySet()) {
							System.out.print(connexions_intermediaire.getClass());
							//Double intens = Double.valueOf(str);
							//connexions.put(indiv, intens);
						}
						System.out.print(connexions + "\n");*/
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//connexions = HashMap<String, String> connexions_intermediaire;
					//System.out.print(connexions + "\n");
					/*System.out.print(connexions_string + "\n");
					for (HashMap.Entry<AID, String> co : connexions_string.entrySet()) {
						System.out.print("for...\n");
						//connexions.put(co.getKey(), Double.valueOf(co.getValue()));
				    }*/
				connexions_set = true;
				//System.out.print("Connections : " + connexions + "\n");
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
				news.setEmetteurInitial(myAgent.getAID().getLocalName());
				//System.out.println("setEmetteurInitial " + news.getEmetteurInitial());
				System.out.println("LA NEWS  intensite = " + news.getIntensite() + " natteints = " + news.getNatteints() + " veracite " +  news.getVeracite());

				//System.out.println("connexions = " + connexions); 
				for (String id : connexions.keySet()) {
					//System.out.println("id = " + id); 
					ACLMessage partage = new ACLMessage(ACLMessage.PROPAGATE);
					partage.addReceiver(new AID(id, AID.ISLOCALNAME));
					partage.setContent(String.valueOf(connexions.get(id)));
					send(partage);
				}
				change_contamination_state(true);
				
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
				//System.out.println(contamine);
				if(!contamine && !readNews) {	
				//aSystem.out.println("individu " + getLocalName() + "  news dans le fil d'actu");
				addBehaviour(new DecisionBehaviour(myAgent, message, Constants.STEP_TIME));
				change_readNews_state(true);
				}
			} else
				block();
		}
	}
		
	public class DecisionBehaviour extends WakerBehaviour {
		ACLMessage message;
		
		public DecisionBehaviour(Agent a, ACLMessage message, long delay) {
			super(a, delay);
			this.message = message;
		}
		
		public void onWake() {
			
			//System.out.println("Individu " + getLocalName() + "a recu la news, ses connections :"+ connexions );
			News news_transmettre = News.getInstance();
			double croire;
			double partage;
		
			double Vr = news_transmettre.getVeracite();
			double In = news_transmettre.getIntensite();
			int Np = news_transmettre.getNpartage();
			Double IntConnexion = Double.valueOf(message.getContent()); 
			
			boolean news_proche = message.getSender().getLocalName().equals(news_transmettre.getEmetteurInitial());
						
			croire = In * Vr*Vr*Vr * (1/esprit_critique) * IntConnexion;
	
				
			if ( In>0.8 ) {
				croire*=5;
			}
			
			else if ( In>0.6 ) {
				croire*=2;
			}
			else if ( In<0.3 ) {
				croire*=0.3;
			}
			else if ( In<0.5 ) {
				croire*=0.5;
			}
						
			if ((news_proche && IntConnexion>0.5)) {
				croire=croire*5;
			}
					
			else if (IntConnexion>0.7) {
				croire*=2;
			}
			else if(IntConnexion<0.3) {
				croire*=0.5;
			}
				
			
			
			if(croire>0.7) {
				news_transmettre.incrementeNatteints();
			}
					   
		    partage=croire * degre_communication;
		    
			if (Np/Constants.NOMBRE_INDIVIDUS>0.8) {
				partage*=2;
			}
			else if (Np/Constants.NOMBRE_INDIVIDUS>0.6) {
				partage*=1.5;
				}
			else if (Np/Constants.NOMBRE_INDIVIDUS<0.2) {
				partage*=0.7;
				}
			
			
			if(partage>0.5) {
				change_contamination_state(true);
				news_transmettre.incrementeNpartage();
				//System.out.println(" Individu " + getLocalName() +" a choisi de partager = " + partage);
	
				for (String id : connexions.keySet()) {
					ACLMessage propagate = new ACLMessage(ACLMessage.PROPAGATE);
					propagate.addReceiver(new AID(id, AID.ISLOCALNAME));					
					propagate.setContent(String.valueOf(connexions.get(id)));
					send(propagate);
				}
			}
		}
	}
	
	
	/* Behaviour qui attend les requetes de news de l'agent Environnement */
	public class WaitforEndingNewsBehaviour extends CyclicBehaviour {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.DISCONFIRM);
		
		@Override
		public void action() {
			
			ACLMessage message = receive(mt);
			
			if (message != null) {
				change_contamination_state(false);
				change_readNews_state(false);
				
			} else {
				block();
			}
				
		}
	}
	
	
	private void change_contamination_state(boolean isContamined) {
		/*
		 * Change this.contamine to bool, and tells simOverview Singleton to fire a proprety change.
		 */
		this.contamine = isContamined;
		simOverview.changeBelieverState(id, this.contamine);
	}
	
	private void change_readNews_state(boolean bool) {
		/*
		 * Change this.readNews to bool, and tells simOverview Singleton to fire a proprety change.
		 */
		this.readNews = bool;
		simOverview.changeReadNews(id, this.readNews);
	}
	
	private void change_connexions_state() {
		/*
		 * tells simOverview Singleton to fire a proprety change on a new connexion
		 * TODO: implement the function
		 * how to get the this.id of an agent based on its AID ?
		 */
		Iterator<Entry<String, Double>> it = connexions.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry<String, Double> pair = (HashMap.Entry<String, Double>)it.next();
	        //it.remove(); // avoids a ConcurrentModificationException
	        
	        //TODO get id from pair.getKey()
	        String[] strFinal = pair.getKey().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
	        int id2 = Integer.parseInt(strFinal[1]) ;
	        double intensite = pair.getValue();
	        
	        simOverview.addConnexion(id, id2 ,intensite);
	    }
		
		
		
	}
	
}
