import Univers.*;
import spaceship.*;
import spaceship.modules.*;

public class Game {
	
	public static Espace Univers;
	
	public static void runGame(){
		
		initGame();
		
		//Boucle du jeu
		while(true){
			
			//Temps initial
			long timeIn = System.nanoTime();
		
			
			updateGame();
			

			//Temps final et calcul du deltatime
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
		
	}
	
	//Boucle de jeu, updater l'univers qui va updater son contenu
	private static void updateGame(){
		
		Univers.update();
		
	}
	
}
