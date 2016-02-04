package GENIA;

public class Genia {
	
	int presentationState = 0;
	float lastTalk = 0;
	
	private boolean lastValue = true;
	


	public void update(){
		
		System.out.println(lastValue + " " + base.Function.say());

		//Si on vient de finir de parler
		if(lastValue!=base.Function.say() && lastValue==false){
			lastTalk = (float) base.Cons.horloge;

		}
		if(base.Function.say()==false){
			lastTalk = (float) base.Cons.horloge;
		}
		lastValue=base.Function.say();
		
		if(presentationState == 0 && (float) base.Cons.horloge - lastTalk>1){
			lastTalk = (float) base.Cons.horloge;
			base.Function.say("Hello, welcome.");
			presentationState = 1;
		}
		
		System.out.println(base.Cons.horloge - lastTalk);
		
		if(presentationState == 1 && (float) base.Cons.horloge - lastTalk>5){
			base.Function.say("Test");
			presentationState = 2;
		}
		
	}
	
}
