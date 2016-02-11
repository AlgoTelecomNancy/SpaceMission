package spaceship.modules;

import spaceship.Module;

public class Room extends Module  {

	public Room(String[] opts){
		this.type = "Room";
		
		this.nbHumains = Float.parseFloat(opts[0]);
	}
	
}
