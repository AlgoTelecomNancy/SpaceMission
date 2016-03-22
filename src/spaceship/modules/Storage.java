package spaceship.modules;

import spaceship.Module;
/**
 * Stockage en carburant
 * 
 * @author Romaric Mollard
 *
 */
public class Storage extends Module  {

	private int storageType = 0; // 0 = fuel; 1 = missiles
	private float capacity = 1000;
	private float value = 0;

	public Storage(String[] opts){
		this.type="Storage";
				
		this.capaciteHumaine = (int)Double.parseDouble(opts[0]);
		
		this.capacity = (float)Double.parseDouble(opts[1]);
		this.value = this.capacity;
		
		this.storageType = Integer.parseInt(opts[2]);
		
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
	
	public int getType(){
		return this.storageType;
	}
	
}
