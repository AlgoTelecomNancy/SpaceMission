package spaceship.modules;

import spaceship.Module;

public class Battery extends Module  {
	
	private float capacity = 1000;
	private float value = 0;

	public Battery(String[] opts){
		this.type = "Battery";
				
		this.capaciteHumaine = (int)Double.parseDouble(opts[0]);
		
		this.capacity = (float)Double.parseDouble(opts[1]);
		this.value = this.capacity;
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
