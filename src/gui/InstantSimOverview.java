package gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Constants;

public class InstantSimOverview {	
	
	PropertyChangeSupport ReadFNews = new PropertyChangeSupport(this);
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

    //Adding something to an attribute
    
    public void changeReadNews(int id, Boolean bool) {
    	fireReadNews(id, bool);
    }
    
    public void addConnexion(int id1, int id2, double intensite) {
    	fireIndividuConnexions( id1,  id2,  intensite);
    }
    
    public void changeBelieverState(int id, Boolean bool) {
    	fireIndividuBelieverList(id, bool);
    }
    
    public void clear() {
    	
    	//TODO: make a function to clear singlton (is it necessary ?)
    	
    }
    
    // ------------------- Listener management -------------
    
    public void addPropertyChangeListenerReadNews(String propertyName, PropertyChangeListener listener) { 
    	ReadFNews.addPropertyChangeListener(propertyName, listener); 
	}
    
    public void addPropertyChangeListenerConnexions(String propertyName, PropertyChangeListener listener) { 
    	changeConnexions.addPropertyChangeListener(propertyName, listener); 
	}
    
    public void addPropertyChangeListenerBelieverList(String propertyName, PropertyChangeListener listener) { 
    	changeBelieverList.addPropertyChangeListener(propertyName, listener); 
	}
    
    private void fireReadNews(int id, boolean read) { 
    	Object[] val = new Object[] { id, read };
    	ReadFNews.firePropertyChange(Constants.READ_NEWS, null, val); 
	}
    
    private void fireIndividuConnexions(int id1, int id2, double intensite) { 
    	Object[] val = new Object[] {id1,  id2,  intensite};
    	changeConnexions.firePropertyChange(Constants.NEW_CONNEXION, null, val); 
	}
    
    private void fireIndividuBelieverList(int id, boolean believeState) { 
    	Object[] val = new Object[] { id, believeState };
    	changeBelieverList.firePropertyChange(Constants.BELIEVER_CHANGE, null, val); 
	}

}