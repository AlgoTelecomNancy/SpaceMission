package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map.Entry;

import main.Main;

public class ThreadJoueur implements Runnable{
	private Thread _t; // contiendra le thread du client
	private Socket _s; // recevra le socket liant au client
  	private PrintWriter _out; // pour gestion du flux de sortie
	private BufferedReader _in; // pour gestion du flux d'entrée
	private Serveur _serveur;
	private int _numClient;
	private boolean run;
	private boolean joueur;
	
	private PlayerOnServer me;
	
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
	
	
	
	
	/* exit : quitte le shell
	 * create -p password -n name  : créer un compte avec tel mot de passe et tel nom
	 * create admin -p password -n name : créer un compte en temps qu'admin avec le mot de passe principale et un nom lambda
	 * connect -i id -n name -p password : se connecter avec id et mot de passe ou nom et mot de passe
	 * (non-Javadoc)
	 */
	
	
	@Override
	public void run() {
		try {
			
			String ip = _s.getRemoteSocketAddress().toString().substring(0,_s.getRemoteSocketAddress().toString().lastIndexOf(":"));
			
			_out.println(ip);
			_out.flush();
			
			
			run = true;
			
			String reponse = "";
			Request request;
			while(run){
				
				request = new Request(reponse, _out);
				
				if (reponse != null){
										
					if(reponse.equals("exit")){ //Quitter
						run = false;
					}else{
						if(me==null){
							if (reponse.equals("help")){
								String answer="create -p password -n name \n"+
										"create admin -p password -n name \n"+
										"connect -i id -n name -p password";
								_out.println(answer);
										
							}else if(reponse.equals("graphics")){ //Only for graphics
								me = _serveur.players.addPlayer(1, ip);
							}else if(request.arg(0).equals("create") && request.isOpt("p") && request.opt("p").equals(Main.password)){ //Administrator (only one)
								if(request.arg(1).equals("admin")){ //Administrator (only one)
									me = _serveur.players.addPlayer(0, ip,Main.password);
								}else{
									String password = null;
									if(request.isOpt("p")){
										password = request.opt("p");
									}
									me = _serveur.players.addPlayer(2, ip, password); //Simple player
									if(request.isOpt("n")){
										me.changeName(request.opt("n"));
									}
								}
								
								if(me!=null){
									me.addReq(new Request("me", _out));
								}
								
							}else if(request.arg(0).equals("connect")){ //Try to connect to existing player
								int id=0;
								String name=null;
								String password=null;
								
								if(request.isOpt("i")){
									id = Integer.parseInt(request.opt("i"));
								}
								if(request.isOpt("n")){
									name = request.opt("n");
								}
								if(!request.isOpt("p") || (id==0 && name==null)){
									_out.println("error, please give a password");
								}
								
								//Search the player
								for(Entry<Integer, PlayerOnServer> player: _serveur.players.players().entrySet()){
									if(player.getValue().getPassword().equals(password) && (player.getKey()==id || player.getValue().getName().equals(name))){
										me = player.getValue();
									}
								}
								
							}else{
								//Tenter de retrouver le joueur avec l'ip
								//Search the player
								for(Entry<Integer, PlayerOnServer> player: _serveur.players.players().entrySet()){
									if(player.getValue().getIp().equals(ip)){
										me = player.getValue();
										me.addReq(request);
									}
								}
							}
														
							if(me!=null){
								_out.println("Connected as player "+me.getId() + " " +me.getIp());
								me.connected=true;
								System.out.println("Le joueur "+me.getId()+" est connecté. (connexion "+_numClient+")");

							}else{
								_out.println("Not connected");
								System.out.println("Un joueur inconnu s'est connecté. (connexion "+_numClient+")");
							}
							_out.flush();
														
						}else{
							me.addReq(request);
						}
					}

				}
				
				if((!run || reponse == null) && me!=null){
					System.out.println("Le joueur "+me.getId()+" s'est deconnecte (connexion "+_numClient+")");
					run = false;
				}else if ((!run || reponse == null) && me==null){
					System.out.println("Un joueur inconnu s'est deconnecte (connexion "+_numClient+")");
					run = false;
				}
				else{
					reponse = _in.readLine();
				}
				
			}
			

			_out.close();
			_in.close();
			_s.close();
		} catch (IOException e) {
			System.out.println("deconnexion d'un joueur (connexion "+_numClient+")");
		}
		
		if(me!=null){
			_serveur.players.players().get(me.getId()).connected = false;
		}
		
		
	}

}
