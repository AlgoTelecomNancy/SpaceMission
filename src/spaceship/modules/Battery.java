package spaceship.modules;

import spaceship.Module;

public class Battery extends Module  {
	
	private float capacity = 0;
	private float value = 0;

	public Battery(String[] opts){
		this.type = "Battery";
	}
	
	public boolean use(float howmany){
		if(value==0){
			return false;
		}
		value = value - Math.abs(howmany);
		return true;
	}
	
	public boolean full(float howmany){
		if(value>=capacity){
			return false;
		}
		value = value + Math.abs(howmany);
		return true;
	}
	
	public float getCapacity(){
		return capacity;
	}
	
	public float getState(){
		return value;
	}
	
}
