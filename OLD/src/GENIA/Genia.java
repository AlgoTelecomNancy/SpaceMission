package GENIA;

public class Genia {
	
	int presentationState = 0;
	float lastTalk = 0;
	
	
	private boolean lastValue = true;
	


	public void update(){
		
		//Si on vient de finir de parler
		if(lastValue!=base.Function.say() && lastValue==false){
			lastTalk = (float) base.Cons.horloge;

		}
		if(base.Function.say()==false){
			lastTalk = (float) base.Cons.horloge;
		}
		lastValue=base.Function.say();
		
		if(presentationState == 5 && (float) base.Cons.horloge - lastTalk>10){
			base.Function.say("Repear main programs then, your last chance to save humanity is for you to find the planet called. Earth.");
			presentationState = 6;
		}
		
		if(presentationState == 4 && (float) base.Cons.horloge - lastTalk>2){
			base.Function.say("Spaceship life scanner terminated, 2521 other people just waked up in rooms 21, 25, and 42. Derivating oxygen. Calculating life time... You will be out of oxygen in 16 minutes.");
			presentationState = 5;
		}
		
		if(presentationState == 3 && (float) base.Cons.horloge - lastTalk>8){
			base.Function.say("Impossible to recover life programs. Unable to control motors power. For your security please repear life program, you will be out of oxygen in 23 hour.");
			presentationState = 4;
		}
		
		if(presentationState == 2 && (float) base.Cons.horloge - lastTalk>2){
			base.Function.say("Apparently mostly of main programms are offline or corrupted, I try to repear them.");
			presentationState = 3;
		}
		
		if(presentationState == 1 && (float) base.Cons.horloge - lastTalk>5){
			base.Function.say("Your cryogenisation didn't worked correctly. It seems to be some other problems in the spaceship, please wait just a minute.");
			presentationState = 2;
		}
		
		if(presentationState == 0 && (float) base.Cons.horloge - lastTalk>7){
			base.Function.say("Good morning. Today is 31 of december of the year 9 9 9 9. An error occured while synchronising time.");
			presentationState = 1;
		}
		
		
	}
	
}
