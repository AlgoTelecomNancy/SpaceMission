package music;

public class Music {
	
	Audio music;
	Audio ambiance;
	
	public Music(){
		ambiance = new Audio("alarm_03.wav");
		music = new Audio("test.wav");            
		music.play();

	}

	public void update(){
		
	}
	
}
