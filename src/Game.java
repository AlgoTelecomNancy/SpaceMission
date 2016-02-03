import Univers.*;
import music.Music;
import spaceship.*;
import spaceship.modules.*;

public class Game {
	
	public static Espace Univers;
	public static Music Sounds;

	public static void runGame(){
		
		//Initialiser le jeu...
		initGame();
		Sounds = new Music();
		
		//Boucle du jeu
		while(true){
			
			//Temps initial
			long timeIn = System.nanoTime();
		
			//On fait un tour de jeu...
			updateGame();
			Sounds.update();

			//Temps final et calcul du deltatime (deltaTime mini = 1ms)
			base.Cons.deltaTime = (double)((long)System.nanoTime() - timeIn)/ 1000000000.0;
			if(base.Cons.deltaTime<0.001){
				try {
				    Thread.sleep(1);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				base.Cons.deltaTime = (double)((long)System.nanoTime() - timeIn)/ 1000000000.0;
			}
			
		}
		
		
	}
	
	//Initialiser le jeu
	private static void initGame(){
		
		//Créer l'espace et l'initialiser
		Univers = new Espace();
		Univers.init();
		
		//Ajouter un joueur (id=0)
		Univers.addPlayer(0);
		Univers.getPlayer(0).vitesse.set(10, 0, 0);
		System.out.println(Univers.getPlayer(0).vitesse);

	}
	
	//Boucle de jeu, updater l'univers qui va updater son contenu
	private static void updateGame(){
		
		//Mise à jour du temps du vaisseau et de l'univers
		base.Cons.universalDeltaTime = base.Cons.deltaTime*Univers.getPlayer(0).tempsRelatif;
		base.Cons.universalHorloge += base.Cons.deltaTime*Univers.getPlayer(0).tempsRelatif;
		base.Cons.horloge += base.Cons.deltaTime;
		
		Univers.update();


	}
	
}
