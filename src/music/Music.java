package music;

import java.io.*;

public class Music {
	
	Audio space_ambiance;
	MultipleAudio musics_classic;
	MultipleAudio musics_attack;
	MultipleAudio musics_danger;

	public Music(){
		//Créer l'ambiance
		space_ambiance = new Audio("spaceship_ambiance.wav");
		space_ambiance.play();
		space_ambiance.loop(true);
		space_ambiance.gain(-20);
		
		//Ajouter les musiques
		musics_classic = new MultipleAudio("musics_classic");
		musics_attack = new MultipleAudio("musics_attack");
		musics_danger = new MultipleAudio("musics_danger");

	    //Lancer la musique
		musics_classic.play();
		musics_classic.gain(0);
		musics_attack.play();
		musics_danger.play();

	}

	public void update(){
		
		//Créer l'ambiance
		if(space_ambiance.gain()<0){
			space_ambiance.gain((float) (space_ambiance.gain()+8*base.Cons.deltaTime));
		}
		
	}

	
}
