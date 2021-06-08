package gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Constants;

public class InstantSimOverview {	
	
	private List<String> IndividuAgents = new ArrayList<String>();
	private HashMap<String, HashMap<String, Double>> connexions = new HashMap<String, HashMap<String, Double>>();
	private HashMap<String, Boolean> believerList = new HashMap<String, Boolean>();
	
	PropertyChangeSupport changeIndividuAgents = new PropertyChangeSupport(this);
	PropertyChangeSupport changeConnexions = new PropertyChangeSupport(this); 
	PropertyChangeSupport changeBelieverList = new PropertyChangeSupport(this); 
	
	// Singleton Pattern Management
	private static final InstantSimOverview instance = new InstantSimOverview();
	
	private InstantSimOverview() {
		
	}
	
    public static final InstantSimOverview getInstance() 
    {
        return instance;
    }
    
    //----------------- getters ---------------
    
    public List<String> getIndividuAgents() 
    {
        return IndividuAgents;
    }
    
    public HashMap<String, Double> getConnexion(String str) 
    {
        return connexions.get(str);
    }
    
    public boolean getBeliever(String str) 
    {
        return believerList.get(str);
    }
    
    //Adding something to an attribute
    
    public void addIndividuAgent(String str) {
    	IndividuAgents.add(str);
    	fireIndividuAgents(str);
    }
    
    public void addConnexion(String str, HashMap<String, Double> map) {
    	connexions.put(str, map);
    	fireIndividuConnexions(str);
    }
    
    public void changeBelieverState(int id, Boolean bool) {
    	fireIndividuBelieverList(id, bool);
    }
    
    public void clear() {
    	
    	//TODO: make a function to clear singlton (is it necessary ?)
    	
    }
    
    // ------------------- Listener management -------------
    
    public void addPropertyChangeListenerIndividuAgents(String propertyName, PropertyChangeListener listener) { 
    	changeIndividuAgents.addPropertyChangeListener(propertyName, listener); 
	}
    
    public void addPropertyChangeListenerConnexions(String propertyName, PropertyChangeListener listener) { 
    	changeConnexions.addPropertyChangeListener(propertyName, listener); 
	}
    
    public void addPropertyChangeListenerBelieverList(String propertyName, PropertyChangeListener listener) { 
    	changeBelieverList.addPropertyChangeListener(propertyName, listener); 
	}
    
    public void addPropertyChangeListenerSimOver(String propertyName, PropertyChangeListener listener) { 
    	changeBelieverList.addPropertyChangeListener(propertyName, listener); 
	}
    
    private void fireIndividuAgents(String val) { 
    	changeIndividuAgents.firePropertyChange("List_Increased", null, val); 
	}
    
    private void fireIndividuConnexions(String val) { 
    	changeConnexions.firePropertyChange("List_Increased", null, val); 
	}
    
    private void fireIndividuBelieverList(int id, boolean believeState) { 
    	Object[] val = new Object[] { id, believeState };
    	changeBelieverList.firePropertyChange(Constants.BELIEVER_CHANGE, null, val); 
	}
    
    private void fireSimOver(boolean isSimOver) { 
    	Object[] val = new Object[] { isSimOver };
    	changeBelieverList.firePropertyChange(Constants.SIMU_OVER, null, val); 
	}
    

}