package spaceship.modules;

import spaceship.Module;

public class Room extends Module  {

	public Room(String[] opts){
		this.type = "Room";

		this.capaciteHumaine = Float.parseFloat(opts[0]);
		this.nbHumains = Float.parseFloat(opts[1]);
	}
	
}
