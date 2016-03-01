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
	public static Visualisation visual = new Visualisation();
	public static void runGame(){
		

		//Initialiser le jeu...
		initGame();
		Sounds = new Music();
		IA = new Genia();
				
		//Boucle du jeu
		while(true){
			
			//Temps initial
			long timeIn = System.nanoTime();

			visual.repaint();
			//visual.fenetre.show();
			//On fait un tour de jeu...
			updateGame();
			Sounds.update();
			
			/*window.getDisplay().getCube(0).angles.z = 20;
			window.getDisplay().getCube(0).angles.y += base.Cons.deltaTime * 100;*/
			
			window.getDisplay().getCube(0).angles.x = 50;
			window.getDisplay().getCube(0).angles.z += base.Cons.deltaTime * 100;

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
		window.getCamera().setPosition(new Vect3D(0.05, 0.00, 0.00));
		window.getCamera().speed = 0.001;
		window.getDisplay().setFocusedCube(0);
		
		for (int x = -1; x < 2; x += 2)
			for (int y = -1; y < 2; y += 2)
				for (int z = -1; z < 2; z += 2)
					window.getDisplay().addCube(new Cube(new Vect3D(x * 0.03, y * 0.03, z * 0.03), new Vect3D(0.001, 0.001, 0.001), new Vect3D(45, 45, 45)));
		
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
