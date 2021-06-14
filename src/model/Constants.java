package model;

public class Constants {
	public static String DEFAULT_SIMULATION_AGENT = "Sim";
	public static Integer NOMBRE_INDIVIDUS = 1000;

	public static String DEFAULT_DEMANDEUR_AGENT = "Demandeur";
	
	public static int MOYENNE_NB_CONNEXION = 150*NOMBRE_INDIVIDUS/10000;
	public static int ECART_TYPE_NB_CONNEXION = 50*NOMBRE_INDIVIDUS/10000;
	
	public static double MOYENNE_ESPRIT_CRITIQUE = 0.7;
	public static double ECART_TYPE_ESPRIT_CRITIQUE = 0.5;
	
	public static double MOYENNE_DEGRE_COMMUNICATION = 0.7;
	public static double ECART_TYPE_DEGRE_COMMUNICATION = 0.5;
	
	public static double MOYENNE_INTENSITE_CONNEXION = 0.7;
	public static double ECART_TYPE_INTENSITE_CONNEXION = 0.5;
	
	public static String BELIEVER_CHANGE = "Believe state changed";
	public static String READ_NEWS = "Read Fake News";
	public static String NEW_CONNEXION = "New connexion";

	public static long STEP_TIME = 2000; //2sec
	public static long NEWS_TO_PARTAGE_TIMEOUT = 4000; //4sec
	
	public static String SIMU_OVER = "simuOver";
	
	public static int WINDOW_SIZE = 600;
	public static int DOT_SIZE = 7;
}

/*
Le nombre de Drunbar :
	3K individus :connections moy 150 	ec= 100:200 -> 50
	
	n indiv : connections moy 150n/3k 	ec= 100n/3k : 200n/3k -> 50n/3k
	
	200 individus: connections moy 10 ec= [6,6; 15]
	individu a au maximum entre 100e t 230 connection. La moyenne étant à 150
	
	application aux réseaux sociaux : https://www.abc.net.au/news/science/2016-01-20/150-is-the-limit-of-real-facebook-friends/7101588
	
*/