package spaceship;
import spaceship.modules.*;
import types.*;

public class Module {
	
	public int id;
	
	//Taille et position du module sur le vaisseau
	public Vect3D position = new Vect3D(0,0,0);
	public Vect3D orientation = new Vect3D(0,0,0);
	public Vect3D positionRelative = new Vect3D(0,0,0);
	public float rayon = 1;
	public float poids = 1;
	
	public String type;

	public float nbHumains = 0; //nombre d'humains
	public float capaciteHumaine = 0; //nombre max d'humains
	public boolean ferme = false; //Sas ouverts ou fermés

	public int dommage = 0; //0 dommage : bon état
	public int temperature = 290; //Température interieure en Kelvin
	public int temperatureMax = 320;
	public int pression = 100; //Pourcentage de la pression normale
	public float pressionTime =0 ; //Temps depuis lequel la pression est mortelle
	public boolean incendie = false; //Incendie (si temp>320 et pression>30)
	public float incendieTime = 0; //temps depuis lequel l'incendie est déclaré
	public boolean alarme = false; //Alarme
	public float coeffmortalite = 0; // coeff de mortalite
	public float coeffsurvie = 0; //coeff de survie
	
	public int[] ArrayModulesTouche = new int[16]; //Max 16 contacts physique avec sas
	public int[] ArrayModulesBranche = new int[64]; //Max 64 contacts branchement cable
	
	public Spaceship myParent;
	
	//Constructeur
	public Module(){
		
	}
	
	
	//Fonction de base pour actualiser le module
	public void update(){
		
		//Incendie
		if(temperature>temperatureMax){
			incendie=true;
		}
		if(pression<30){
			incendie=false;
		}
				
		//Alarme
		alarme = incendie; // Si incendie, alarme déclenchée
		alarme = pression<50 && nbHumains>0; //Si pression basse ou trop élevée et humain alarme
		alarme = pression<50 && nbHumains>0;
		
		/*
		//Incendie augmente température
		if(incendie && Math.random()>0.98 && temperature<390){
			temperature += 1;
		}
		//Pas d'incendie stabilise la température à 290K
		if(!incendie && Math.random()>0.78 && temperature!=290){
			temperature += -(290-temperature)/(290-temperature);
		}
		*/
		
		
		
		
		//Tuer des humains
		coeffmortalite = 0;
		coeffsurvie = 1;
		//Température (ça marche pas mal...)
		if(Math.random()>0.4){
			coeffmortalite = (float)Math.min(Math.max(0,(0.0045*Math.pow((temperature-290),2)-3)/10),1);
			coeffsurvie = (float) (coeffsurvie*(1-Math.pow(coeffmortalite,2)));
			coeffmortalite = 0;
		}
		
		//Incendie
		if(incendie){
			incendieTime += (float)base.Cons.deltaTime;  // augmente le temps de l'incendie
			if (incendieTime>10){
				coeffmortalite = Math.min(incendieTime/2000,1); //incendie de plus en plus mortel avec le temps
				coeffsurvie = (float) (coeffsurvie*(1-Math.pow(coeffmortalite,2)));
				coeffmortalite = 0;
			}
		} else if (incendieTime!=0){
			incendieTime = 0;
		}
		
		
		//Pression basse
		if(pression<50){
			pressionTime += (float) base.Cons.deltaTime;
			if (pressionTime>45){		// après 45 secondes en sous oxygène, tout le monde est mort d'intoxication
				nbHumains = 0;
			};
		} else if (pressionTime != 0){
			pressionTime = 0;
		}
		
		
		nbHumains = (float) Math.max(0, nbHumains*(1-(1-coeffsurvie)*base.Cons.deltaTime/0.05));
		/*
		//Gérer les flux @TODO
		if(!ferme){
			
		}
		*/
		
	}
	
	public void updatePositionRel(Vect3D centreGrav){
		this.positionRelative.translate(centreGrav.multiply(-1));
	}
	
	
}
