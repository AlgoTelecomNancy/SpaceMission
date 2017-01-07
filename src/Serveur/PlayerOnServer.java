package Serveur;

import java.util.LinkedList;

public class PlayerOnServer {

	private LinkedList<Request> reqList;
	private int level; //0 administrator, 1 graphics, 2 player
	private int id;
	private String name = "<unknown>"; //Player name
	private String password = "";
	private String ip = "";
	
	public boolean connected = false;
	
	//Create a player with level and socket output
	public PlayerOnServer(int l, int id, String ip, String password){

		this.level = l;
		this.reqList = new LinkedList<Request>();
		this.id = id;
		this.connected = true;
		this.ip = ip;
		
		if(password==null){
			this.password = generatePassword();
		}else{
			this.password = password;
		}
		
	}
	
	/**
	 * Add a request
	 * @param req
	 */
	public void addReq(Request req){
		this.reqList.push(req);
	}
	
	/**
	 * Do all requests 
	 */
	public void doReq(){

		if(this.reqList.size()>0){
		
			for(Request req: this.reqList){
				ReqController.run(req, this);
			}
			this.reqList.clear();
		
		}
	}
	
	
	public int getId(){
		return id;
	}
	public String getIp(){
		return ip;
	}
	public String getName(){
		return name;
	}
	public int getLevel(){
		return this.level;
	}
	public String getPassword(){
		return password;
	}
	public void changeName(String name){
		this.name = name;
	}
	
	
	public static String generatePassword(int nb){
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String code = "";
		for(int i=0; i<nb; i++){
			code = code + AB.charAt((int)(Math.random()*AB.length()));
		}
		return code;
	}
	public static String generatePassword(){
		return generatePassword(6);
	}
	
}
