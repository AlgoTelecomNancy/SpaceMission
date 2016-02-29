import Univers.*;
import display.Cube;
import display.Window;
import music.Music;
import types.Vect3D;
import GENIA.*;

public class Game {
	
	public static Espace Univers;
	public static Music Sounds;
	public static Genia IA;
	public static Window window;
	
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
		window = new Window();
		window.getDisplay().addCube(new Cube(new Vect3D(), new Vect3D(), new Vect3D()));
		window.getCamera().setPosition(new Vect3D(20, 20, 20));
		
		for (int x = -1; x < 2; x += 2)
			for (int y = -1; y < 2; y += 2)
				for (int z = -1; z < 2; z += 2)
					window.getDisplay().addCube(new Cube(new Vect3D(x * 10, y * 10, z * 10), new Vect3D(1, 1, 1), new Vect3D(45, 45, 45)));
		
		Univers = new Espace(window);
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
