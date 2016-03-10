package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadJoueur implements Runnable{
	private Thread _t; // contiendra le thread du client
	private Socket _s; // recevra le socket liant au client
  	private PrintWriter _out; // pour gestion du flux de sortie
	private BufferedReader _in; // pour gestion du flux d'entrée
	private Serveur _serveur;
	private int _numClient;
	private boolean run;
	
	public ThreadJoueur(Socket s, Serveur serveur){
		_s = s;
		_serveur = serveur;
		try
	    {
	      // fabrication d'une variable permettant l'utilisation du flux de sortie avec des string
	      _out = new PrintWriter(_s.getOutputStream());
	      // fabrication d'une variable permettant l'utilisation du flux d'entrée avec des string
	      _in = new BufferedReader(new InputStreamReader(_s.getInputStream()));
	    }
	    catch (IOException e){ }

	    _t = new Thread(this); // instanciation du thread
	    _t.start(); // demarrage du thread, la fonction run() est ici lancée
	}
	
	@Override
	public void run() {
		try {
			// send hello pour le fun :)
			_out.print("hello !!!\n");
			_out.flush();
			// lit le premier message du client
			String reponse = _in.readLine();
			System.out.println("reponse : " + reponse);
			// si c'est un joueur
			if (reponse.equals("joueur")){
				// ajoute le flux de sortie au serveur
				_numClient = _serveur.addJoueur(_out);
				System.out.println("envoie du message");
				_out.print("vous etes le joueur "+_numClient);
				_out.flush();
				run = true;
				// boucle de communication
				while(run){
					System.out.println("attente de reponse joueur "+_numClient);
					reponse = _in.readLine();
					System.out.println("reponse : "+reponse);
					_out.print("bien recu");
					_out.flush();
				}
			}
			// TODO: faire la gestion des clients graphiques
			
		} catch (IOException e) {
			System.out.println("deconnexion du joueur "+_numClient);
			//_serveur.removeJoueur(_numClient);
		}

		// si c'est un client graphique
		// ajouter le printWriter a serveur en temps que graphique
		// sinon c'est un joueur
		// boucle en fonction de si c'est un joueur ou un graphique
	}

}
