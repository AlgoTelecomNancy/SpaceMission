package Serveur;
import types.*;
import spaceship.modules.*;
import java.util.ArrayList;

import core.Game;

public class ReqController {
	
	public static void run(Request req, PlayerOnServer player){
		player.write("Evaluate => "+req);
	}
	
	public static boolean traiter(ArrayList<String> requetes){
		for (String requete : requetes) {
			System.out.println(requete);
			
			if(requete.equals("add")){
				((Engine)core.Game.Univers.joueurs[0].modules[1]).setState(100);
			}
			
			if(requete.equals("remove")){
				((Engine)core.Game.Univers.joueurs[0].modules[1]).setState(0);
			}
			
			
		}
		return true;
	}
	
	public static void addPlayer(){
		Game.Univers.addPlayer(0);
	}
	
}
