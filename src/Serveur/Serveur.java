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
	
	private Serveur(){
		port = 18000;
		clientGraphiques = new ArrayList<>();
		clientJoueurs = new ArrayList<>();
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
			ServerSocket ss = new ServerSocket(port);
			run = true;
			while (run){
				System.out.println("attente d'une connexion...");
				new ThreadJoueur(ss.accept(), serveur);
			}
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public synchronized int addJoueur(PrintWriter _out) {	
		clientJoueurs.add(_out);
		System.out.println("ajout du joueur "+clientJoueurs.size());
		return clientJoueurs.size();
	}
	
	public synchronized void removeJoueur(int id){
		clientJoueurs.remove(id);
	}
	
	public synchronized int addGraphique(PrintWriter _out){
		clientGraphiques.add(_out);
		return clientGraphiques.size();
	}
	
	
}
