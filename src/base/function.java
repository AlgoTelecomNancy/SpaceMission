package base;

public class function {
	
	//Return true at a realistic frequency (not regular)
	//This is for a game whith a 50ms refresh
	public static boolean frequency(float f){
	
		//0.050 is the game frequency
		if(Math.random()<(1-0.050*f)){
			return false;
		}
		return true;
		
	}
	
}
