import spaceship.*;
import spaceship.modules.*;

public class game {
	
	public static Module test;
	
	public static void runGame(){
		
		initGame();
		
		//Boucle du jeu
		while(true){
			
			//Temps initial
			long timeIn = System.nanoTime();
		
			
			updateGame();
			

			//Temps final et calcul du deltatime
			base.cons.deltaTime = (double)((long)System.nanoTime() - timeIn)/ 1000000000.0;
			if(base.cons.deltaTime<0.001){
				try {
				    Thread.sleep(1);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				base.cons.deltaTime = (double)((long)System.nanoTime() - timeIn)/ 1000000000.0;
			}

		}
		
		
	}
	
	private static void initGame(){
		
		test = new Canon();
		
	}
	
	private static void updateGame(){
		
		((Canon)test).update();
		
	}
	
}
