package spaceship.modules;
import spaceship.*;

public class Command extends Module {

	public Command(int id){
		this.id = id;
		this.capaciteHumaine = 0;
		this.poids = 100+750; //100kg + passagers
		this.position.set(0, 0, 0); // Position centrale
		this.rayon = 5; //5 mètres de rayon
		
				
	}
	
}
