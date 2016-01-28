import spaceship.*;
import spaceship.modules.*;

public class main {
	public static void main(String[] args){
		
		Module[] modules = new Module[10];
		
		modules[0] = new Canon();
		modules[0].nbHumains=1000;
		modules[0].incendie=true;
		
		for(int i=0;i<10000;i++){
			
			((Canon)modules[0]).update();
			
			System.out.println(modules[0].incendie + " " +modules[0].temperature + " " + modules[0].nbHumains + " " + modules[0].pression);
		
			if(i>0 && i<1000 && i%10==0){
				modules[0].temperature+=-1;
			}
			
			if(i>1000 && i<2000 && i%10==0){
				modules[0].temperature+=1;
			}
			
			
			try {
			    Thread.sleep(50);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			
		}
	}
}
