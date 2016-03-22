package spaceship.modules;

import spaceship.Module;
import types.Vect3D;

public class Engine extends Module  {
	
	

	public Engine(String[] opts){
		this.type = "Engine";
	}
	
	
	public void update(){
		
		myParent.addAcceleration(new Vect3D(1,1,1));
		
	}
	
}
