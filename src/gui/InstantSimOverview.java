package gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import model.Constants;

public class InstantSimOverview {
	private static int NOMBRE_INDIVIDUS = Constants.NOMBRE_INDIVIDUS;
	private int NB_Personnes_Croyantes;
	PropertyChangeSupport changes = new PropertyChangeSupport(this); 
	
	// Singleton Pattern Management
	private static final InstantSimOverview instance = new InstantSimOverview();
	
	private InstantSimOverview() {
		//TODO Delete after testing
		NB_Personnes_Croyantes = NOMBRE_INDIVIDUS/3;
	}
	
    public static final InstantSimOverview getInstance() 
    {
        return instance;
    }
    
    public int get_NOMBRE_INDIVIDUS() {
    	return NOMBRE_INDIVIDUS;
    }
    
    public int get_NB_Personnes_Croyantes() {
    	return NB_Personnes_Croyantes;
    }
    
    public void set_NB_Personnes_Croyantes(int param) {
    	NB_Personnes_Croyantes = param;
    	fire(NB_Personnes_Croyantes);
    }
    
    public void increment_NB_Personnes_Croyantes() {
    	NB_Personnes_Croyantes++;
    	fire(NB_Personnes_Croyantes);
    }
    
    // ------------------- Listener management -------------
    
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) { 
    	changes.addPropertyChangeListener(propertyName, listener); 
	}
    
    private void fire(int val) { 
    	 changes.firePropertyChange("NB_Personnes_Croyantes_Increased", null, val); 
    	} 

}