package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import java.sql.Timestamp;
import java.util.Random;


public class News {
	private static double veracite;
	private static double intensite;
	private static String emetteurInitial;  
	private static int n_partage = 0;
	private static int n_atteints = 0;
	private static int profondeur = 0;
	private static Timestamp startTime;
	private static Timestamp timeLastIndivPartage;
	
	PropertyChangeSupport  changes = new PropertyChangeSupport(this);
	Boolean newsOver;
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		changes.addPropertyChangeListener(propertyName, listener);
	}

	private static final News instance = new News();
	
	private News() {
		timeLastIndivPartage = new Timestamp(System.currentTimeMillis());
		startTime = new Timestamp(System.currentTimeMillis());
	}
	
	/*
	public News(double v, double i, String e) {
		this.veracite = v;
		this.intensite = i;
		this.emetteurInitial = e;
		this.n_partage=0;
	}
	*/
	
    public static final News getInstance() 
    {
        return instance;
    }
	
	public int getNpartage() {
		return n_partage;
	}
	
	public void incrementeNpartage() {
		this.n_partage+=1;
		timeLastIndivPartage = new Timestamp(System.currentTimeMillis());
	}
	
	public int getNatteints() {
		return n_atteints;
	}
	
	public void incrementeNatteints() {
		this.n_atteints+=1;
	}
	
	public double getVeracite() {
		return veracite;
	}

	public void setVeracite(double veracite) {
		this.veracite = veracite;
	}

	public double getIntensite() {
		return intensite;
	}
	
	public double getProfondeur() {
		return profondeur;
	}
	
	public void setProfondeur(int profondeur) {
		this.profondeur = profondeur;
	}
	
	public void setIntensite(double intensite) {
		this.intensite = intensite;
	}

	public String getEmetteurInitial() {
		return emetteurInitial;
	}

	public void setEmetteurInitial(String emetteurInitial) {
		this.emetteurInitial = emetteurInitial;
	}

	public Timestamp getTimeLastIndivPartage() {
		return timeLastIndivPartage;
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}
	

	public boolean isTimedout() {
		long now = new Timestamp(System.currentTimeMillis()).getTime(); 
		/*
		System.out.println("now " + now );
		System.out.println("timeLastIndivPartage.getTime() " + timeLastIndivPartage.getTime() );
		System.out.println("Constants.NEWS_TO_PARTAGE_TIMEOUT = " +  Constants.NEWS_TO_PARTAGE_TIMEOUT);
		System.out.println("now - timeLastIndivPartage.getTime() = " + (now - timeLastIndivPartage.getTime()) );
		System.out.println("now - timeLastIndivPartage.getTime() > Constants.NEWS_TO_PARTAGE_TIMEOUT = " + (boolean) (now - timeLastIndivPartage.getTime() > Constants.NEWS_TO_PARTAGE_TIMEOUT ));
		*/
		return  now - timeLastIndivPartage.getTime() > Constants.NEWS_TO_PARTAGE_TIMEOUT;
	}
	
	public static void generateNews() {
		//on réinitialise les parametres
		timeLastIndivPartage = new Timestamp(System.currentTimeMillis());
		startTime = new Timestamp(System.currentTimeMillis());
		profondeur = 0;
		n_partage = 0;
		n_atteints = 0;
		
		// veracite demandee a  l'utilisateur et intensite en decoule
		Random random1 = new Random();
		veracite = random1.nextDouble();
		if (veracite == 0) {
			veracite = 0.01;
		}
		Random random2 = new Random();
		System.out.println("Veracite =  " + veracite + "\n");
		
		intensite=random2.nextDouble();    
		
		/*
		if (veracite > 0.7) { //  0.8 < veracite < 1 => 0.3 < intensite < 1
			 if (a>0.9) { a-=0.1; }
			 intensite = 0.1 + a;
			 System.out.println("0.1 = " + a + "\n");
			 
		}
		else if (veracite > 0.4) { //  0,4 < veracite < 0,8 => 0.5 < intensite < 1
			if (a>0.7) { a-=0.3; }
			intensite = 0.3 + a;
			System.out.println("0.3 = " + a + "\n");
		}
		else { //  0 < veracite < 0,4 => 0.7 < intensite < 1
			if (a>0.3) { a-=0.7; }
			intensite = 0.7 + a;
			System.out.println("0.7 = " + a + "\n");
		}
		*/
		
		System.out.println("Intensite = " + intensite + "\n");
	}
	

}