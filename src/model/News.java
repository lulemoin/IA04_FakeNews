package model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;


public class News {
	public double veracite;
	public double intensite;
	public String emetteurInitial;  
	public int n_partage;

	public News() {
		
	}
	
	public News(double v, double i, String e) {
		this.veracite = v;
		this.intensite = i;
		this.emetteurInitial = e;
		this.n_partage=0;
	}
	
	public int getNpartage() {
		return n_partage;
	}
	
	public void incrementeNpartage() {
		this.n_partage+=1;
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
	
	
	public String toJSON() {
		ObjectMapper mapper = new ObjectMapper();
		String s = "";
		try {
			s = mapper.writeValueAsString(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	public static News read(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		News p = null;
		try {
			p = mapper.readValue(jsonString, News.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
	

}
