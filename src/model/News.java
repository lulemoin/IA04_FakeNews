package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import java.sql.Timestamp;


public class News {
	private double veracite;
	private double intensite;
	private String emetteurInitial;  
	private int n_partage = 0;
	private int n_atteints = 0;
	private Timestamp timeLastIndivPartage;
	
	PropertyChangeSupport  changes = new PropertyChangeSupport(this);
	Boolean newsOver;
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		changes.addPropertyChangeListener(propertyName, listener);
	}

	private static final News instance = new News();
	
	private News() {
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

	public boolean isTimedout() {
		long now = new Timestamp(System.currentTimeMillis()).getTime(); 
		return  now - timeLastIndivPartage.getTime() > Constants.NEWS_TO_PARTAGE_TIMEOUT;
	}

}
