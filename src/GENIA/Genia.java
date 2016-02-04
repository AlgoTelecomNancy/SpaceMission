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
		
		
		if(presentationState == 1 && (float) base.Cons.horloge - lastTalk>1){
			base.Function.say("My voice changed, but I am still the same.");
			presentationState = 2;
		}
		
		if(presentationState == 0 && (float) base.Cons.horloge - lastTalk>1){
			base.Function.say("Hello again. Do you remenber me ?");
			presentationState = 1;
		}
		
		
	}
	
}
