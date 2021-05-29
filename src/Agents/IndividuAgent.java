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
	private News news_instance;
	
	HashMap<AID, Double> connexions = new HashMap<AID, Double>();
	
	protected void setup() {
		System.out.println(getLocalName() + "--> Installed");
		
		//récupération des objets passés en parametres
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

		
		// Initialisation des paramètres suivant une loi normale
		
		//TO-DO : à couper-coller dans la fonction setup_connexions (qui sera executé quand tous les agents seront intialisés)

		Random r = new Random();
		
		news_instance = News.getInstance();
		
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
				
				for (AID id : connexions.keySet()) {
					ACLMessage newsTransmise = new ACLMessage(ACLMessage.PROPAGATE);
					newsTransmise.addReceiver(id);
				    newsTransmise.setContent(news_instance.toJSON());
					send(newsTransmise);
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
				// Créer la news et la partage à ses connexions sous la forme d'un message de type PROPAGATE
				
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
			//TODO utiliser l'instance News pour récupérer les bons agryuments
			News news_transmettre=News.read(message.getContent());
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
			double Vr=news_transmettre.getVeracite();
			double In=news_transmettre.getIntensite();
			int Np=news_transmettre.getNpartage();
			
			System.out.println(esprit_critique + degre_communication + "\n");
			
			croire=In * Vr*Vr * (1/esprit_critique);
			/* 
			 * AJOUTER Intensité connexion Ic : recupérer le Double du HashMap
			 * */
			
			partage=croire * Np/Constants.NOMBRE_INDIVIDUS * degre_communication;
			if(partage>0.5) {
				news.add(news_transmettre);
				news_transmettre.incrementeNpartage();
				
				for (AID id : connexions.keySet()) {
					ACLMessage newsTransmise = new ACLMessage(ACLMessage.PROPAGATE);
					newsTransmise.addReceiver(id);
					newsTransmise.setContent(message.getContent());
					// newsTransmise.setContent(news.toJSON());
					send(newsTransmise);
				}

				
				
			}
		
				
			}
		
		}
	
}
