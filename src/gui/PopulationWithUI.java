package gui;

import java.awt.Color;
import javax.swing.JFrame;

import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;
import sim.engine.*;
import sim.display.*;




public class PopulationWithUI extends GUIState {
	public static int FRAME_SIZE = 600;
	public Display2D display;
	public JFrame displayFrame;
	SparseGridPortrayal2D yardPortrayal = new SparseGridPortrayal2D();
	
	public PopulationWithUI(SimState state) {
		super(state);
	}
	public static String getName() {
		return "Simulation de propagation de Fake News"; 
	}
	public void start() {
	  super.start();
	  setupPortrayals();
	}

	public void load(SimState state) {
	  super.load(state);
	  setupPortrayals();
	}
	public void setupPortrayals() {
	  Population beings = (Population) state;	
	  yardPortrayal.setField(beings.yard );
	  yardPortrayal.setPortrayalForClass(model.Insectes.class, getInsectesPortrayal());
	  yardPortrayal.setPortrayalForClass(model.Nourriture.class, getNourriturePortrayal());
	  yardPortrayal.setGridLines(true);
	  yardPortrayal.setGridColor(Color.GRAY);
	  yardPortrayal.setGridModulus(1);
	  yardPortrayal.setGridLineFraction(0.01);
	  display.reset();
	  display.repaint();
	}
	private OvalPortrayal2D getInsectesPortrayal() {
		OvalPortrayal2D r = new OvalPortrayal2D();
		r.paint = Color.RED;
		r.filled = true;
		return r;
	}
	
	private OvalPortrayal2D getNourriturePortrayal() {
		OvalPortrayal2D r = new OvalPortrayal2D();
		r.paint = Color.YELLOW;
		r.filled = true;
		return r;
	}
	
	public void init(Controller c) {
		  super.init(c);
		  display = new Display2D(FRAME_SIZE,FRAME_SIZE,this);
		  display.setClipping(false);
		  displayFrame = display.createFrame();
		  displayFrame.setTitle("Beings");
		  c.registerFrame(displayFrame); // so the frame appears in the "Display" list
		  displayFrame.setVisible(true);
		  display.attach( yardPortrayal, "Yard" );
	}
	public  Object  getSimulationInspectedObject()  {  return  state;  }
	public  Inspector  getInspector() {
	Inspector  i  =  super.getInspector();
	  i.setVolatile(true);
	  return  i;
	}
}
