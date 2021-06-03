package model;

public class Constants {
	public static String DEFAULT_SIMULATION_AGENT = "Sim";
	public static Integer NOMBRE_INDIVIDUS = 5;

	public static String DEFAULT_DEMANDEUR_AGENT = "Demandeur";
	
	public static int MOYENNE_NB_CONNEXION = 4;
	public static int ECART_TYPE_NB_CONNEXION = 1;
	
	public static double MOYENNE_ESPRIT_CRITIQUE = 0.7;
	public static double ECART_TYPE_ESPRIT_CRITIQUE = 0.5;
	
	public static double MOYENNE_DEGRE_COMMUNICATION = 0.7;
	public static double ECART_TYPE_DEGRE_COMMUNICATION = 0.5;
	
	public static double MOYENNE_INTENSITE_CONNEXION = 0.7;
	public static double ECART_TYPE_INTENSITE_CONNEXION = 0.5;
}

/*
Le nombre de Drunbar :
	3K individus :connections moy 150
	200 individus: connections moy 10 ec= [6,6; 15]
	individu a au maximum entre 100e t 230 connection. La moyenne étant à 150
	
	application aux réseaux sociaux : https://www.abc.net.au/news/science/2016-01-20/150-is-the-limit-of-real-facebook-friends/7101588
	
*/