import Univers.*;
import music.Music;
import GENIA.*;

public class Game {
	
	public static Espace Univers;
	public static Music Sounds;
	public static Genia IA;

	public static void runGame(){
		
		//Initialiser le jeu...
		initGame();
		Sounds = new Music();
		IA = new Genia();
		
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
		Univers.sons = Sounds;
		Univers.ia = IA;
		
		//Ajouter un joueur (id=0)
		Univers.addPlayer(0);

	}
	
	//Boucle de jeu, updater l'univers qui va updater son contenu
	private static void updateGame(){
				
		//Mise à jour du temps du vaisseau et de l'univers
		base.Cons.universalDeltaTime = base.Cons.deltaTime*Univers.getPlayer(0).tempsRelatif;
		base.Cons.universalHorloge += base.Cons.deltaTime*Univers.getPlayer(0).tempsRelatif;
		base.Cons.horloge += base.Cons.deltaTime;
		
		Univers.update();
		IA.update();


	}
	
}
