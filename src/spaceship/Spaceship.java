package spaceship;
import spaceship.modules.*;
import types.*;

public class Spaceship {
	
	//Base
	public Position position = new Position();
	public Vect3D orientation = new Vect3D();
	public int myId = 0;
	
	public float horlogeExterne = 0; //Relativité restreinte
	public float tempsRelatif = 1; //Relativité restreinte
	public float masseRelative = 1; //Bon c'est de la vulgarisation, mais on s'en sert directement pour le calcul de la quantité de mouvemenet comme ça !
	
	public Vect3D vitesse = new Vect3D();
	public Vect3D acceleration = new Vect3D();
	public Vect3D vitesseRot = new Vect3D();
	public Vect3D accelerationRot = new Vect3D();
	
	public Module[] modules = new Module[200];
	public int nbModules = 0;
	
	
	public float masseTotale = 0;
	public Vect3D centreGravite = new Vect3D();
	
	public Vect3D[] accelerations = new Vect3D[100]; // Au maximum 100 accélérations, et on enlève les plus faibles au fur et à mesure si dépassement
	public Vect3D[] moments = new Vect3D[100]; // Au maximum 100 moments, et on enlève les plus faibles au fur et à mesure si dépassement

	
	public Spaceship(int id){
		this.myId = id;
		
		//Créer le module principal d'id 0
		modules[0] = new Command(0);
		
		
		//@TODO Ajouter les modules selon fichier de configuration
		
		//@TODO Configurer les liens entre modules
		
		//Mettre à jour le nombre de modules
		for(int i=0; i<modules.length;i++){
			if(modules[i]!=null){
				nbModules++;
			}
		}
				
	}
	
	public void update(){
				
		//Mettre à jour centre de gravité relatif au centre virtuel et masse totale
		masseTotale = 0;
		for(int i=0; i<nbModules;i++){
			masseTotale += modules[i].poids;
		}
		centreGravite.set(0, 0, 0);
		for(int i=0; i<nbModules;i++){
			centreGravite.x += modules[i].position.x*modules[i].poids;
			centreGravite.y += modules[i].position.y*modules[i].poids;
			centreGravite.z += modules[i].position.z*modules[i].poids;
		}
		centreGravite.x = centreGravite.x/masseTotale;
		centreGravite.y = centreGravite.y/masseTotale;
		centreGravite.z = centreGravite.z/masseTotale;
		///////////
		
		//Vider l'array de vecteur accélération et moment
		accelerations = new Vect3D[accelerations.length];
		moments =  new Vect3D[moments.length];
		
		
		//Mettre à jour les modules
		for(int i=0; i<nbModules;i++){
			modules[i].update();
		}
		//////////
		
		//Mise à jour de l'accélération totale et moment total
		acceleration.set(0, 0, 0);
		int i =0;
		while(accelerations[i]!=null && i<accelerations.length){
			acceleration.x += accelerations[i].x;
			acceleration.y += accelerations[i].y;
			acceleration.z += accelerations[i].z;
			
			i++;
		}
		accelerationRot.set(0, 0, 0);
		i =0;
		while(moments[i]!=null && i<moments.length){
			accelerationRot.x += moments[i].x;
			accelerationRot.y += moments[i].y;
			accelerationRot.z += moments[i].z;
			
			i++;
		}
		///////
		
		//Mise à jour vitesse
		vitesse.translate(acceleration.multiply(base.Cons.universalDeltaTime));
		vitesseRot.translate(accelerationRot.multiply(base.Cons.universalDeltaTime));
		vitesseRot.modulo(360); //Pour éviter de trop grands nombres
		//////
		
		
		//Mise à jour de la position avec la vitesse
		position.translate(vitesse.multiply(base.Cons.universalDeltaTime));
		orientation.translate(vitesseRot.multiply(base.Cons.universalDeltaTime));
		orientation.modulo(360);//Pour éviter de trop grands nombres

		//Relativité et horloge
		tempsRelatif = (float) (1/Math.sqrt(1-Math.pow(vitesse.size()/299792,2)));
		masseRelative = (float) (1/Math.sqrt(0.97-Math.min(Math.pow((vitesse.size())/299792,0.9),0.9699999)));
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
