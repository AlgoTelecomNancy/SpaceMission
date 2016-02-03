import spaceship.*;
import spaceship.modules.*;

public class Game {
	
	public static Module test;
	
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
	
	private static void initGame(){
		
		test = new Canon();
		((Canon)test).nbHumains = 1000000;
		((Canon)test).incendie = true;

	}
	
	private static void updateGame(){
		
		((Canon)test).update();
		System.out.println(((Canon)test).nbHumains + " " + ((Canon)test).incendieTime);

		
	}
	
}
