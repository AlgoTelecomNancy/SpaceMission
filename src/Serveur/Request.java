package Serveur;

import java.util.*;

public class Request {

	private String rawReq;
	private HashMap<String,String> opts;
	private ArrayList<String> args;

	public Request(String req){
		
		if(req==null || req.length()<2){
			return;
		}
		
		this.rawReq = req;
		this.args = new ArrayList<String>(Arrays.asList(req.split(" ")));

		this.opts = new HashMap<String,String>();

		boolean lookForOption = false;
		String option = "";
		for(String arg: args){
			if(lookForOption==false){
				if(arg.charAt(0)=='-'){
					option = arg.substring(1);
					lookForOption = true;
				}
			}else{
				opts.put(option, arg);
				lookForOption = false;
			}
		}
	}
	
	public Boolean isOpt(String index){
		return opts.containsKey(index);
	}
	public String opt(String index){
		if(!opts.containsKey(index)){ return null; }
		return opts.get(index);
	}
	public String arg(int index){
		if(index<0 || index>=args.size()){ return null; }
		return args.get(index);
	}
	
	@Override
	public String toString(){
		return rawReq;
	}
	
}
