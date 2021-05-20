package model;

public class News {
	float veracite;
	float intensite;
	String emetteurInitial;  

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
	
	

}
