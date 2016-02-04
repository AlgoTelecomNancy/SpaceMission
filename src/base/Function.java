package base;

import java.io.IOException;

public class Function {
	
	//Return true at a realistic frequency (not regular)
	//This is for a game whith a 50ms refresh
	public static boolean frequency(float f){
	
		//0.050 is the game frequency
		if(Math.random()<(1-0.050*f)){
			return false;
		}
		return true;
		
	}
	
	
	//To speak
	public static Process speak;
	
	public static void say(String s){
		
		//Seulement sur mac...
		if(System.getProperty("os.name").toLowerCase().indexOf("mac") < 0){
			return;
		}
		
		if(speak==null){
			try {
				speak = Runtime.getRuntime().exec("say \""+s+"\"");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}
	
	//Return true if we can speak.
	public static boolean say(){
		if(speak==null){
			return true;
		}
		try 
	    {
	        speak.exitValue();
	        speak=null;
	    } 
	    catch(IllegalThreadStateException e) 
	    {
	        return false;
	    }
		if(speak==null){
			return true;
		}
		return false;
	}
	
}
