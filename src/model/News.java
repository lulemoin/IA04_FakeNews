package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;

//Intensité, véracité, n_partage, n_atteints, profondeur -> to_csv
public class News {
	private double veracite;
	private double intensite;
	private String emetteurInitial;  
	private int n_partage = 0;
	private int n_atteints = 0;
	private int profondeur= 0;
	private static final News instance = new News();
	public List<String> feuilleAgents = new ArrayList<String>();
	
	private News() {
	}
	
    public static final News getInstance() 
    {
        return instance;
    }
	
    
    public void addFeuilleAgent() {
    	//feuilleAgents.add(string);
    	// message.getSender().getLocalName()
    }
    
	public int getNpartage() {
		return n_partage;
	}
	
	public void incrementeNpartage() {
		this.n_partage+=1;
	}
	
	public int getNatteints() {
		return n_atteints;
	}
	
	public void incrementeNatteints() {
		this.n_atteints+=1;
	}
	
	public int getProfondeur() {
		return profondeur;
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

}
