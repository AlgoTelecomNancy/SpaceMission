package spaceship;
import types.*;

public class Module {
	
	//Taille et position du module sur le vaisseau
	public Vect3D position = new Vect3D(0,0,0);
	public float rayon = 1;

	public int nbHumains = 0; //nombre d'humains
	public int capaciteHumaine = 0; //nombre max d'humains
	public boolean ferme = false; //Sas ouverts ou fermés

	public int dommage = 0; //0 dommage : bon état
	public int temperature = 290; //Température interieure en Kelvin
	public int temperatureMax = 320;
	public int pression = 100; //Pourcentage de la pression normale
	public boolean incendie = false; //Incendie (si temp>320 et pression>30)
	public boolean alarme = false; //Alarme

	public int[] ArrayModulesTouche = new int[16]; //Max 16 contacts physique avec sas
	public int[] ArrayModulesBranche = new int[64]; //Max 64 contacts branchement cable
	
	
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
		
		//Incendie augmente température
		if(incendie && Math.random()>0.98 && temperature<390){
			temperature += 1;
		}
		//Pas d'incendie stabilise la température à 290K
		if(!incendie && Math.random()>0.78 && temperature!=290){
			temperature += -(290-temperature)/(290-temperature);
		}
		
		
		
		
		//Tuer des humains
		
		//Température (ça marche pas mal...)
		if(Math.random()>0.4){
			float probaDeMourrir = (float)Math.min(Math.max(0,(0.0045*Math.pow((temperature-290),2)-3)/10),1);
			nbHumains = nbHumains - (int)((nbHumains*probaDeMourrir+1)*Math.random()*Math.random());
		}
		/*
		
		//Incendie : 2 morts par seconde
		if(incendie && Math.random()>0.9){
			nbHumains += -0.001*nbHumains-1;
		}
		//Pression basse : 0.5 morts par seconde
		if((pression<70 || pression>130) && Math.random()>0.95){
			nbHumains += -0.001*nbHumains-1;
		}
		
		//Temperature très mauvaise : 10 morts par seconde
		if(temperature>350 || pression<50){
			nbHumains += -0.01*nbHumains-1;
		}
		nbHumains = Math.max(0, nbHumains);
		
		//Gérer les flux @TODO
		if(!ferme){
			
		}
		*/
		
	}
	
	
}
