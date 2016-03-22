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
	private boolean joueur;
	private Controller controller;
	
	public ThreadJoueur(Socket s, Serveur serveur){
		_s = s;
		_serveur = serveur;
		try
	    {
	      // fabrication d'une variable permettant l'utilisation du flux de sortie avec des string
	      _out = new PrintWriter(_s.getOutputStream());
	      // fabrication d'une variable permettant l'utilisation du flux d'entrée avec des string
	      _in = new BufferedReader(new InputStreamReader(_s.getInputStream()));
	      System.out.println("IP client : "+s.getInetAddress());
	    }
	    catch (IOException e){ }

	    _t = new Thread(this); // instanciation du thread
	    _t.start(); // demarrage du thread, la fonction run() est ici lancée
	}
	
	@Override
	public void run() {
		try {
			// send hello pour le fun :)
			_out.println("hello !!!");
			_out.flush();
			// lit le premier message du client
			String reponse = _in.readLine();
			System.out.println("reponse : " + reponse);
			// si c'est un joueur
			if (reponse.equals("joueur")){
				joueur = true;
				// ajoute le flux de sortie au serveur
				_numClient = _serveur.addJoueur(_out);
				System.out.println("envoie du message");
				_out.println("vous etes le joueur "+_numClient);
				_out.flush();
				run = true;
				// boucle de communication
				while(run){
					System.out.println("attente de reponse joueur "+_numClient);
					reponse = _in.readLine();
					if (reponse != null){
						System.out.println("reponse : "+reponse);
						if (controller.traiter(reponse)){
							_out.println("bien recu");
							_out.flush();
						} else {
							_out.println("erreur: commande non valide");
							_out.flush();
						}
						
					} else {
						System.out.println("le joueur "+_numClient+" c'est deconnecte");
						run = false;
					}
				}
			}
			// TODO: faire la gestion des clients graphiques
			
			
			// fermeture des flux et du socket
			if (joueur)
				_serveur.delJoueur(_out);
			else
				_serveur.delGraphique(_out);
			_out.close();
			_in.close();
			_s.close();			
		} catch (IOException e) {
			System.out.println("deconnexion du joueur "+_numClient);
			if (joueur)
				_serveur.delJoueur(_out);
			else
				_serveur.delGraphique(_out);
			//_serveur.removeJoueur(_numClient);
		}
	}

}
