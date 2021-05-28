package model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;


public class News {
	public float veracite;
	public float intensite;
	public String emetteurInitial;  

	public News() {
		
	}
	
	public News(float v, float i, String e) {
		this.veracite = v;
		this.intensite = i;
		this.emetteurInitial = e;
	}

	public float getVeracite() {
		return veracite;
	}

	public void setVeracite(float veracite) {
		this.veracite = veracite;
	}

	public float getIntensite() {
		return intensite;
	}

	public void setIntensite(float intensite) {
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
