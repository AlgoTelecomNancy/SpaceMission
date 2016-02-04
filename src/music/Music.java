package music;

import java.io.*;

public class Music {
	
	Audio space_ambiance;
	MultipleAudio musics_activity;
	MultipleAudio musics_attack;
	MultipleAudio musics_classic;
	MultipleAudio musics_sad;
	MultipleAudio musics_start;
	MultipleAudio musics_suspens;
	MultipleAudio musics_travel;
	
	boolean started = false;
	float activity = 0;
	float danger = 0;
	float attack = 0;
	float damages = 0;
	boolean attacked = false;
	boolean hasbeensad = false;

	public Music(){
		//Créer l'ambiance
		space_ambiance = new Audio("spaceship_ambiance.wav");
		space_ambiance.play();
		space_ambiance.loop(true);
		space_ambiance.gain(-20);

		//Ajouter les musiques
		musics_activity = new MultipleAudio("musics_activity");
		musics_attack = new MultipleAudio("musics_attack");
		musics_classic = new MultipleAudio("musics_classic");
		musics_sad = new MultipleAudio("musics_sad");
		musics_start = new MultipleAudio("musics_start");
		musics_suspens = new MultipleAudio("musics_suspens");
		musics_travel = new MultipleAudio("musics_travel");
		
	    //Lancer la musique
		musics_start.play();
		musics_start.gain(-5);

		musics_activity.play();
		musics_attack.play();
		musics_sad.play();
		musics_classic.play();
		musics_travel.play();
		musics_suspens.play();

		started = false;
		
	}

	public void update(){
		
		//Créer l'ambiance
		if(space_ambiance.gain()<0){
			space_ambiance.gain((float) (space_ambiance.gain()+8*base.Cons.deltaTime));
		}
		
		musics_start.update();
		musics_activity.update();
		musics_attack.update();
		musics_sad.update();
		musics_classic.update();
		musics_travel.update();
		musics_suspens.update();
		
		if(!started && musics_start.ending()<5){
			musics_start.fadeOut(5);
			musics_travel.fadeIn(5);
			started = true;
		}

				
		
	}

	
}
