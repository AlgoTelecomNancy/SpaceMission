package spaceship.modules;
import spaceship.*;

public class Command extends Module {

	public Command(){
		
		
		this.type = "Command";
		
		this.capaciteHumaine = 0;
		this.poids = 100+750; //100kg + passagers
		this.position.set(0, 0, 0); // Position centrale
		this.rayon = 0.005f; //5 mètres de rayon
		
				
	}
	
}
