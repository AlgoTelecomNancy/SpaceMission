package spaceship.modules;

import spaceship.Module;
import types.Vect3D;

public class Engine extends Module  {
	
	private float state = 0;
	private int choosedMode = 2;
	
	private int mode = 2;
	private double maxForce = 0.01;
	private double batteryUse = 0.1;
	private double carburantUse = 0;
	

	public Engine(String[] opts){
		this.type = "Engine";
	}
	
	
	public void update(){
		
		if(state == 100){
			myParent.addMoment(new Vect3D(1,0,0));
		}
		
	}
	
	
	public void setState(float value){
		
		if(value <= 0){
			this.state = 0;
		}else{
			
			//Si le mode est 1 ou 2 on ne fait que mettre au maximum les moteurs
			if(choosedMode <= 1){
				this.state = 100;
			}else{
			//Sinon on met un pourcentage
				this.state = Math.min(value, 100);
			}
			
		}
		
	}
	
	public void setMode(int value){
		
		//On empeche de sortir du mode par défaut
		if(value<=mode && value>=0){
			
			this.choosedMode = value;
			
		}
		
	}
	
	public int getMode(){
		return this.mode;
	}
	
	public float getForce(){
		return (float)(this.state*this.maxForce);
	}
	
	public float getMaxForce(){
		return (float)(this.maxForce);
	}
	
}
