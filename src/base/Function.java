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
	
	public static void say(String str){
		if(speak==null){
			try {
				speak = Runtime.getRuntime().exec("say \""+str+"\"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//Return true if we can speak.
	public static boolean say(){
		try {
			if(speak.waitFor()==0){
				speak = null;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(speak==null){
			return true;
		}
		return false;
	}
	
}
