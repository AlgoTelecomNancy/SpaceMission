package Serveur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Thread Serveur (singleton)
 * @author carl
 *
 */
public class Serveur implements Runnable{
	private static Serveur serveur;
	private static Thread t;
	private int port;
	private volatile boolean run;
	private volatile ArrayList<PrintWriter> clientGraphiques;
	private volatile ArrayList<PrintWriter> clientJoueurs;
	private int newIdJoueur;
	private volatile ArrayList<String> listRequetes; // liste des requetes en attente de traitement
	
	/**
	 * init les listes et le port par defaut a 18000
	 */
	private Serveur(){
		newIdJoueur = 0;
		port = 18000;
		clientGraphiques = new ArrayList<>();
		clientJoueurs = new ArrayList<>();
		listRequetes = new ArrayList<>();
	}
	
	/**
	 * si le serveur n'existe pas, cree le serveur dans un nouveau thread
	 * sinon retourne le serveur deja cree
	 * @return le serveur
	 */
	public static Serveur getServeur(){
		if (serveur == null) {
			serveur = new Serveur();
			t = new Thread(serveur);
			t.start();
		}
		return serveur;
	}

	@Override
	public void run() {
		try {
			// creer le socket
			ServerSocket ss = new ServerSocket(port);
			System.out.println("IP serveur : "+ss.getInetAddress());
			run = true;
			while (run){
				System.out.println("attente d'une connexion...");
				// creer un nouveau thread pour chaque nouveau joueur
				new ThreadJoueur(ss.accept(), serveur);
			}
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * ajoute un joueur
	 * @param _out flux de sortie
	 * @return numero du joueur
	 */
	public synchronized int addJoueur(PrintWriter _out) {
		newIdJoueur++;
		clientJoueurs.add(_out);
		System.out.println("ajout du joueur "+clientJoueurs.size());
		return newIdJoueur;
	}
	
	public synchronized void delJoueur(PrintWriter _out){
		clientJoueurs.remove(_out);
	}
	
	/**
	 * ajoute une requete dans la file d'attente
	 * @param requete
	 */
	public synchronized void addRequete(String s){
		listRequetes.add(s);
	}
	
	/**
	 * ajoute un client graphique
	 * @param _out flux de sortie
	 * @return numero du client
	 */
	public synchronized int addGraphique(PrintWriter _out){
		clientGraphiques.add(_out);
		return clientGraphiques.size();
	}
	
	public synchronized void delGraphique(PrintWriter _out){
		clientGraphiques.remove(_out);
	}
	
	/**
	 * retourne une copie de la liste des requetes en attentes
	 * @return copie de la liste des requetes en attentes
	 */
	public ArrayList<String> getCloneRequetes(){
		return (ArrayList<String>)this.listRequetes.clone();
	}
	
	/**
	 * vide la liste des requetes
	 */
	public synchronized void clearRequetes(){
		this.listRequetes.clear();
	}
}
