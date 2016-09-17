package Serveur;

import java.io.*;
import java.util.*;

public class PlayerOnServer {

	private LinkedList<Request> reqList;
	private PrintWriter _out;
	private int level; //0 administrator, 1 graphics, 2 player
	private int id;
	private String name = "<unknown>"; //Player name
	private String password = "";
	private String ip = "";
	
	public boolean connected = false;
	
	//Create a player with level and socket output
	public PlayerOnServer(int l, PrintWriter o, int id, String ip, String password){
		this._out = o;
		this.level = l;
		this.reqList = new LinkedList<Request>();
		this.id = id;
		this.connected = true;
		this.ip = ip;
		
		if(password==null){
			this.password = base.Function.generatePassword();
		}else{
			this.password = password;
		}
		
		this.write("{\"id\":\""+id+"\",\"password\":\""+password+"\"}");
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
	
	/**
	 * Say something to the player
	 */
	public void write(String data){
		if(this.connected){
			this._out.println(data);
			this._out.flush();
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
	public String getPassword(){
		return password;
	}
	public void changeName(String name){
		this.name = name;
	}
	
}
