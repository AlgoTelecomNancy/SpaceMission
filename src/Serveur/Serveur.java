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

	public PlayerList players;
	
	/**
	 * init les listes et le port par defaut a 18000
	 */
	private Serveur(){
		port = 18000;
		players = new PlayerList();
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
			System.out.println("IP serveur : "+ss.getInetAddress()+" port "+port);
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
	
}
