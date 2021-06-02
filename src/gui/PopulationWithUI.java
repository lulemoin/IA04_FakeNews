package gui;

import sim.portrayal.network.*;
import sim.portrayal.continuous.*;
import sim.engine.*;
import sim.display.*;
import sim.portrayal.simple.*;
import sim.portrayal.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;

public class PopulationWithUI extends GUIState
{
	public Display2D display;
	public JFrame displayFrame;
	
	ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();
	NetworkPortrayal2D buddiesPortrayal = new NetworkPortrayal2D();
	
	public static void main(String[] args)
	{
		PopulationWithUI vid = new PopulationWithUI();
		Console c = new Console(vid);
		c.setVisible(true);
	}
	
	public PopulationWithUI() { super(new Population( System.currentTimeMillis())); }
	public PopulationWithUI(SimState state) { super(state); }
	
	public static String getName() { return "Fake News propagation simulation"; }
	
	public Object getSimulationInspectedObject() { return state; }
	
	public Inspector getInspector()
	{
		Inspector i = super.getInspector();
		i.setVolatile(true);
		return i;
	}
	
	public void start()
	{
		super.start();
		setupPortrayals();
	}
	
	public void load(SimState state)
	{
		super.load(state);
		setupPortrayals();
	}
	
	public void setupPortrayals()
	{
		Population population = (Population) state;
		// tell the portrayals what to portray and how to portray them
		yardPortrayal.setField( population.yard );
		yardPortrayal.setPortrayalForAll(new OvalPortrayal2D(3)
			{
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info){
				IndividuAgentMason individu = (IndividuAgentMason)object;
				if (individu.believer)
					paint = Color.RED;
				else
					paint = Color.GREEN;
				super.draw(object, graphics, info);
				}
			});
			
		buddiesPortrayal.setField( new SpatialNetwork2D( population.yard, population.buddies ) );
		buddiesPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());
		
		
		// reschedule the displayer
		display.reset();
		display.setBackdrop(Color.white);
		// redraw the display
		display.repaint();
	}
	public void init(Controller c)
	{
		super.init(c);
		
		display = new Display2D(600,600,this);
		display.setClipping(false);
		
		displayFrame = display.createFrame();
		displayFrame.setTitle("Fake News propagation simulation");
		c.registerFrame(displayFrame); // so the frame appears in the "Display" list
		displayFrame.setVisible(true);
		display.attach( buddiesPortrayal, "Buddies" );
		display.attach( yardPortrayal, "Yard" );
	}
	
	public void quit()
	{
		super.quit();
		if (displayFrame!=null) displayFrame.dispose();
		displayFrame = null;
		display = null;
	}
}