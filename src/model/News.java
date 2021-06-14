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
	
	private final Object monitorN_partage = new Object();
	private final Object monitorN_atteints = new Object();
	private final Object monitorTimeLastIndivPartage = new Object();
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		changes.addPropertyChangeListener(propertyName, listener);
	}

	private static final News instance = new News();
	
	private News() {
		timeLastIndivPartage = new Timestamp(System.currentTimeMillis());
		startTime = new Timestamp(System.currentTimeMillis());
	}
	
    public static final News getInstance() 
    {
        return instance;
    }
	
	public int getNpartage() {
		synchronized (monitorN_partage) {
		      return n_partage;
		    }
	}
	
	public void incrementeNpartage() {
		synchronized (monitorN_partage) {
			this.n_partage+=1;
			synchronized (monitorTimeLastIndivPartage) {
				timeLastIndivPartage = new Timestamp(System.currentTimeMillis());
			}
		}
	}
	
	public int getNatteints() {
		synchronized (monitorN_atteints) {
			return n_atteints;
		}
		
	}
	
	public void incrementeNatteints() {
		synchronized (monitorN_atteints) {
			this.n_atteints+=1;
		}
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
		synchronized (monitorTimeLastIndivPartage) {
			return timeLastIndivPartage;
		}
		
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}
	

	public boolean isTimedout() {
		synchronized (monitorTimeLastIndivPartage) {
			long now = new Timestamp(System.currentTimeMillis()).getTime(); 
			return  now - timeLastIndivPartage.getTime() > Constants.NEWS_TO_PARTAGE_TIMEOUT;
		}
	}
	
	public static void generateNews() {
		System.out.println("\n LE NOMBRE DE PARTAGE A CET ESSAI EST DE  " + n_partage);
		System.out.println("\n LE NOMBRE D ATTEINTS A CET ESSAI EST DE  " + n_atteints);
		//on r�initialise les parametres
		timeLastIndivPartage = new Timestamp(System.currentTimeMillis());
		
		startTime = new Timestamp(System.currentTimeMillis());
		profondeur = 0;
		
		n_partage = 0;
		n_atteints = 0;
		
		// veracite demandee a� l'utilisateur et intensite en decoule
		Random random1 = new Random();
		veracite = random1.nextDouble();
		if (veracite == 0) {
			veracite = 0.01;
		}
		Random random2 = new Random();
		System.out.println("Veracite =  " + veracite + "\n");
		
		intensite=random2.nextDouble();    
		
		
		if (veracite > 0.8) { 	 
			 intensite = 0.1 + random2.nextDouble()*90/100;
		}
		
		else if (veracite > 0.4) { 
			intensite = 0.5 + random2.nextDouble()*50/100;
		}
		
		else { 
			intensite = 0.7 + random2.nextDouble()*30/100;
		}
		
		System.out.println("Intensite = " + intensite + "\n");
	}
	

}