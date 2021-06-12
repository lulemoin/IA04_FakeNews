package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CsvFile {
	
	private static String chemin="C:/Users/camom/OneDrive/Documents/CoursP21/IA04/Result_simulation.csv";  
	//private PrintWriter writer;
	//private StringBuilder sb;
	
	private static final CsvFile instance = new CsvFile();
	
	
	public static final CsvFile getInstance() {
	 		return instance;
	}
	
	
	public void setFile() {
		try (PrintWriter writer = new PrintWriter(new File(this.chemin))) {
			
		      StringBuilder sb = new StringBuilder();
		      sb.append("veracite, intensite, n_partage, n_atteints, profondeur");
		      sb.append('\n');
		  
		      writer.write(sb.toString());
	
		      //this.writer=writer;
		      //this.sb=sb;
	
		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
	}
	public String getchemin() {
		return chemin;
	}
	/*
	public PrintWriter getwriter() {
		return writer;
	}
	public StringBuilder getsb() {
		return sb;
	}
	*/

}