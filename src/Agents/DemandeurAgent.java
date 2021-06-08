package Agents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREInitiator;
import jade.util.leap.Serializable;

import model.Constants;
import model.News;

import java.io.IOException;
import java.lang.Math.*;
import java.sql.Timestamp;


public class DemandeurAgent extends Agent {
	
	public List<String> IndividuAgents = new ArrayList<String>();
	//static DemandeurAgent instance;
	public int done = 0;
	public News news;

	protected void setup() {
		System.out.println(getLocalName() + "--> Installed");
		//addBehaviour(new WaitSubscriptions());
		SequentialBehaviour sequence = new SequentialBehaviour();
		
		sequence.addSubBehaviour(new WaitSubscriptions());//nb_individus � passer
		sequence.addSubBehaviour(new SetupIndividus());
		sequence.addSubBehaviour(new SelectIdReceiver(this, 1000));
		
		addBehaviour(sequence);
		addBehaviour(new waitForTerminaisonSimulation(this, 2000));
	}
	
	
	//mise en place du design pattern singleton
	//possibilit� de faire en fait plusieurs instances ? Aurait-ce un int�r�t ?
//	public static DemandeurAgent getInstance() {
//		if(instance == null) {
//			instance = new DemandeurAgent();
//		}
//		return instance;
//	}
//	
		
	//Behaviour d'attente des souscriptions des individus
	private class WaitSubscriptions extends Behaviour {

		public void action() {
			//le behaviour attend des messages subscribe
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE);
			ACLMessage message = receive(mt);
			
			// si un message est re�u, on ajoute l'aid du sender
			if (message != null) {
				if (!IndividuAgents.contains(message.getSender().getLocalName())) {
					IndividuAgents.add(message.getSender().getLocalName());
				}
				//sinon on r�pond par un message failure
				else {
					System.out.printf(message.getSender()+ "deja dans la liste");
				}
			} else
				block();
		}

		public boolean done() {
			return (IndividuAgents.size() == Constants.NOMBRE_INDIVIDUS);
		}
	}
	
	private class SelectIdReceiver extends WakerBehaviour {

		public SelectIdReceiver(Agent a, long timeout) {
			super(a, timeout);
		}

		public void onWake() {
		
			int idx = (int) Math.floor(Math.random()*IndividuAgents.size());
			String idReceiver = IndividuAgents.get(idx);
			System.out.println("idx = " + IndividuAgents.get(idx) + " a toi de lancer la news");
			ACLMessage demande = new ACLMessage(ACLMessage.REQUEST);
			demande.addReceiver(new AID(idReceiver, AID.ISLOCALNAME));
			myAgent.addBehaviour(new Send(myAgent, demande));
		}
	}
	// voir s�lectionner sp�cifique ID receiver
	private class Send extends AchieveREInitiator {

		public Send(Agent a, ACLMessage msg) {
			super(a, msg);
		}
		
		
		private void HandleInform() {
			done = 1;
			news = News.getInstance();			 
		}
		
		private void HandleRefuse() {
			System.out.println("L'individu refuse de traiter la news");
		}
	}
	
	private class SetupIndividus extends OneShotBehaviour {

		public void action() {
			Iterator<String> it = IndividuAgents.iterator();
			while(it.hasNext()) {
				String aid = it.next();
				Random r = new Random();
				int nb_connexions = -1;
				while (nb_connexions < 1 || nb_connexions >= Constants.NOMBRE_INDIVIDUS) {
					nb_connexions = (int) Math.round(r.nextGaussian()) * Constants.ECART_TYPE_NB_CONNEXION + Constants.MOYENNE_NB_CONNEXION ;
				}
				//HashMap<AID, Double> connexions = new HashMap<AID, Double>();
				HashMap<String, Double> connexions = new HashMap<String, Double>();
				//Connexions connexions = new Connexions();
				
				List<String> RandIndividuAgents = new ArrayList<String>(IndividuAgents);
				RandIndividuAgents.remove(aid);
				Collections.shuffle(RandIndividuAgents);
				
				for (int j = 0 ; j < nb_connexions ; j++) {
					String nom = RandIndividuAgents.remove(0);
					double intensite = -1;
					while (intensite <= 0 || intensite > 1)
						intensite = r.nextGaussian() * Constants.ECART_TYPE_INTENSITE_CONNEXION + Constants.MOYENNE_INTENSITE_CONNEXION;	
					connexions.put(nom, intensite);
				}
				
				/*List<String> connexions_string;
				connexions.forEach((c) -> {
					connexions_string.add(c.toJSON());
				});*/ 
				ACLMessage connexions_msg = new ACLMessage(ACLMessage.INFORM);
				connexions_msg.addReceiver(new AID(aid, AID.ISLOCALNAME));
				try {
					connexions_msg.setContentObject(connexions);
				}catch (NoClassDefFoundError e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				send(connexions_msg);
			}
		}
	}

	
	private class waitForTerminaisonSimulation extends TickerBehaviour {
	
		public waitForTerminaisonSimulation(Agent a, long period) {
			super(a, period);
			
		}
	
		@Override
		protected void onTick() {
			if (News.getInstance().isTimedout()) {
				long now = new Timestamp(System.currentTimeMillis()).getTime();
				long lifeTime = now - News.getInstance().getStartTime().getTime();				
				int profondeur = Math.round(lifeTime/Constants.STEP_TIME) - 2;
				System.out.println("profondeur  " + profondeur);
				//ecrire dans le fichier les outputs
				//arreter la simu
				
				News.getInstance().setProfondeur(profondeur);
				
				
				doDelete();
			}
			
		}
		
	}
}

	
////doit avoir un oeil sur les fakes news en cours
//	// des qu'une fake news est finie, il en renvoie une nouvelle ?
//	private class Manager extends Behaviour {
//
//		public void action() {
//			if(IndividuAgents.size()!=0) {
//				addBehaviour(new SelectIdReceiver());
//			}	
//			
//			// notification quand la news est finie ou garder une r�f�rence dessus
//			
//		}
//
//		public boolean done() {
//			return false;
//		}
//		
//	}
//	
//	//quel est le tick ?
//	// envoyer des messages uniquement quand la liste n'est pas vide

//	private class getProfondeur extends OneShotBehaviour {
//		String racine=news.getEmetteurInitial();
//	}
	
	
	
	