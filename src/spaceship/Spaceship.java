package spaceship;
import types.*;

public class Spaceship {
	
	//Base
	public Position position = new Position();
	public int myId = 0;
	
	public float horlogeExterne = 0; //Relativité restreinte
	public float tempsRelatif = 1; //Relativité restreinte
	public float masseRelative = 1; //Bon c'est de la vulgarisation, mais on s'en sert directement pour le calcul de la quantité de mouvemenet comme ça !
	
	public Vect3D vitesse = new Vect3D();
	public Vect3D acceleration = new Vect3D();

	
	public Spaceship(int id){
		this.myId = id;
	}
	
	public void update(){
		
		//Mise à jour de la position avec la vitesse
		position.translate(vitesse.multiply(base.Cons.universalDeltaTime));
		
		//Relativité et horloge
		tempsRelatif = (float) (1/Math.sqrt(1-Math.pow(vitesse.size()/299792,2)));
		masseRelative = (float) (1/Math.sqrt(0.97-vitesse.size()/299792)); // @TODO trouver une bonne formule
		horlogeExterne += base.Cons.deltaTime*tempsRelatif;

		verification();
	}
	
	private void verification(){
		//Dépassement de vitesse maximum (0.99*c)
		if(vitesse.size()>296000){
			vitesse = vitesse.multiply(296000/vitesse.size());
		}
	}
	
}
