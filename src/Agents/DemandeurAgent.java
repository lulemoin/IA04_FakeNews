package Agents;

import java.util.ArrayList;
import java.util.Collections;
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
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREInitiator;
import model.Constants;
import java.lang.Math.*;


public class DemandeurAgent extends Agent {
	
	public List<AID> IndividuAgents = new ArrayList<AID>();
	//static DemandeurAgent instance;
	public int done = 0;
	public AID news;

	protected void setup() {
		System.out.println(getLocalName() + "--> Installed");
		//addBehaviour(new WaitSubscriptions());
		SequentialBehaviour sequence = new SequentialBehaviour();
		
		sequence.addSubBehaviour(new WaitSubscriptions());//nb_individus à passer
		sequence.addSubBehaviour(new SetupIndividus());
		sequence.addSubBehaviour(new SelectIdReceiver());
		
		addBehaviour(sequence);
	}
	
	
	//mise en place du design pattern singleton
	//possibilité de faire en fait plusieurs instances ? Aurait-ce un intérêt ?
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
			
			// si un message est reçu, on ajoute l'aid du sender
			if (message != null) {
				System.out.printf("demande recue \n");
				if (!IndividuAgents.contains(message.getSender())) {
					IndividuAgents.add(message.getSender());
					System.out.printf(message.getSender()+ "ajouté à la liste \n");
				}
				//sinon on répond par un message failure
				else {
					System.out.printf(message.getSender()+ "déjà dans la liste");
				}
			} else
				block();
		}

		public boolean done() {
			return (IndividuAgents.size() == Constants.NOMBRE_INDIVIDUS);
		}


	}
	
	private class SelectIdReceiver extends OneShotBehaviour {

		public void action() {
		
			int idx = (int) Math.floor(Math.random()*IndividuAgents.size());
			AID idReceiver = IndividuAgents.get(idx);
			ACLMessage demande = new ACLMessage(ACLMessage.REQUEST);
			demande.addReceiver(idReceiver);
			myAgent.addBehaviour(new Send(myAgent, demande));
		}
	}
	// voir sélectionner spécifique ID receiver
	private class Send extends AchieveREInitiator {

		public Send(Agent a, ACLMessage msg) {
			super(a, msg);
		}
		
		
		private void HandleInform() {
			done = 1;
			//news = ??
		}
		
		private void HandleRefuse() {
			myAgent.addBehaviour(new SelectIdReceiver());
		}
	}
	
	private class SetupIndividus extends OneShotBehaviour {

		public void action() {
			Iterator<AID> it = IndividuAgents.iterator();
			while(it.hasNext()){
				AID aid = it.next();
				for (int i = 1 ; i <= Constants.NOMBRE_INDIVIDUS ; i++) {
					Random r = new Random();
					int nb_connections = -1;
					while (nb_connections < 0)
						nb_connections = (int) Math.round(r.nextGaussian()) * Constants.ECART_TYPE_NB_CONNEXION + Constants.MOYENNE_NB_CONNEXION ;
					
					List<AID> RandIndividuAgents = new ArrayList<AID>();
					Collections.copy(IndividuAgents, RandIndividuAgents);
					Collections.shuffle(RandIndividuAgents);
					for (int j = 0 ; j < nb_connections ; i++) {
						String nom = random_individus.remove(0);
						double intensite = -1;
						while (intensite < 0 && intensite > 1)
							intensite = Math.round(r.nextGaussian()) * Constants.ECART_TYPE_INTENSITE_CONNEXION + Constants.MOYENNE_INTENSITE_CONNEXION;					
						// TO-DO addConnection()
						// addConnection(nom, intensite);
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
//			// notification quand la news est finie ou garder une référence dessus
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


	