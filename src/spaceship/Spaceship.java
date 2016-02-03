package spaceship;
import types.*;

public class Spaceship {
	
	//Base
	public Position position = new Position();
	public int myId = 0;

	public Vect3D vitesse = new Vect3D();
	public Vect3D acceleration = new Vect3D();

	
	public Spaceship(int id){
		this.myId = id;
	}
	
	public void update(){
		
		//Mise à jour de la position avec la vitesse
		position.translate(vitesse.multiply(base.Cons.deltaTime));
		
	}
	
}
