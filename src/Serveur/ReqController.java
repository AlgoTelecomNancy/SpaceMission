package Serveur;
import types.*;
import spaceship.modules.*;
import java.util.ArrayList;
import java.util.Map.Entry;

import core.Game;

public class ReqController {
	
	public static void run(Request req, PlayerOnServer player){
		boolean valid = false;
		if(player.getLevel()==0){ //Administrateur
			valid = valid || adminFunctions(req, player);
		}
		if(player.getLevel()==2){ //Player
			valid = valid || playersFunctions(req, player);
		}
		if(player.getLevel()==1){ //Graphics
			valid = valid || graphicsFunctions(req, player);
		}
		valid = valid || commonFunctions(req, player);
		if(!valid && !(req+"").equals("<null>")){
			req.write("{'error':'unknown command','command':'"+req+"'}");
		}
	}
	

	
	
	
	private static boolean adminFunctions(Request req, PlayerOnServer player) {
		
		boolean valid = false;
		
		if(req.arg(0).equals("mainpassword")){
			if(req.arg(1)!=null){
				Game.password = req.arg(1);
				req.write("{'password':'"+Game.password+"'}");
			}else{
				req.write("{'password':'"+Game.password+"'}");
			}
			valid = true;
		}
		
		return valid;
	}





	private static boolean playersFunctions(Request req, PlayerOnServer player) {
		// TODO Auto-generated method stub
		return false;
	}





	private static boolean graphicsFunctions(Request req, PlayerOnServer player) {
		// TODO Auto-generated method stub
		return false;
	}





	private static boolean commonFunctions(Request req, PlayerOnServer player) {
		boolean valid = false;
		
		if(req.arg(0).equals("players")){
			
			String result = "[";
			
			for(Entry<Integer, PlayerOnServer> p : Game.serveur.players.players().entrySet()){
				result += "{'id':'"+p.getKey()+"'"
						+"'pseudo':'"+p.getValue().getName()+"'"
						+"'connected':'"+p.getValue().connected+"'"
						+"}";
			}
			
			req.write(result.substring(0, result.length()-1)+"]");
			valid = true;
		}
		
		return valid;
	}





	public static void addPlayer(){
		Game.Univers.addPlayer(0);
	}
	
}
