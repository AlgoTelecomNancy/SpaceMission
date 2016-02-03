package music;
import  sun.audio.*;
import  java.io.*;

public class Music {
	
	Audio music;
	Audio ambiance;
	
	public Music(){
		ambiance = new Audio("alarm_03.wav");
		music = new Audio("test.wav");            
		music.start();

	}

	public void update(){
		System.out.println(music.getTime());
	}
	
}
