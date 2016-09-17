package Serveur;

import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

public class PlayerList {

	private Hashtable<Integer,PlayerOnServer> playersList;
	private int idIncrement;
	private boolean hasAdmin = false;
	
	public PlayerList(){
		playersList = new Hashtable<Integer,PlayerOnServer>();
		idIncrement = 1;
	}
	
	public Hashtable<Integer,PlayerOnServer> players(){
		return this.playersList;
	}
	
	public PlayerOnServer addPlayer(int level, PrintWriter o, String ip, String password){
		if(level == 0){
			if(hasAdmin){
				return null; //Do not add multiple admins
			}
			hasAdmin = true;
		}
		PlayerOnServer player = new PlayerOnServer(level, o, idIncrement, ip, password);
		this.playersList.put(idIncrement, player);
		return player;
	}
	public PlayerOnServer addPlayer(int level, PrintWriter o, String ip){
		return addPlayer(level, o, ip, null);
	}
	public void removePlayer(int id){
		this.playersList.remove(id);
	}
	
	public void doReq(){
		for(Entry<Integer, PlayerOnServer> player: playersList.entrySet()){
			player.getValue().doReq();
		}
	}
	
}
