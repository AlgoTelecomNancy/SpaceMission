package music;

import java.io.File;
import javax.sound.sampled.*;

public class Audio {

	Clip clip;
	AudioInputStream me;
	
	public long position = 0; // Position en microsecondes
	
	public Audio(String path){
		try{
			
	        clip = AudioSystem.getClip();
	        // getAudioInputStream() also accepts a File or InputStream
	        me = AudioSystem.getAudioInputStream( new File("assets/"+path) );
			clip.open(me);

		}
		catch(Exception exc){
		    System.out.println("Failed to play the file."+exc);
		}
		
		
	}
	
	//Ci dessous gestion de play pause...
	public void play(){
		try{
			clip.setMicrosecondPosition(position);
			clip.start();

		}catch(Exception exc){
		    System.out.println("Failed to play the file."+exc);
		}
	}
	
	public double getElapsedTime(){
		return (double)clip.getMicrosecondPosition()/1000000.0;
	}
	public double getTime(){
		return (double)clip.getMicrosecondLength()/1000000.0;
	}
	
	public void stop(){
		clip.stop();
	}
	
	public void pause(){
		position = clip.getMicrosecondPosition();
		clip.stop();
	}
	
	//Ci dessous gestion du gain
	public void gain(float val){
	
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		if(val>gainControl.getMaximum()){
			val = gainControl.getMaximum();
		}
		if(val<gainControl.getMinimum()){
			val = gainControl.getMinimum();
		}
		gainControl.setValue(val);
	}
	
	public float gain(){
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		return gainControl.getValue();
	}
	
	
	//Ci dessous gestion de la panoramique
	public void angle(float val){
		if(val>1){
			val = 1;
		}
		if(val<-1){
			val = -1;
		}
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
		gainControl.setValue(val);
	}
	
	public float angle(){
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
		return gainControl.getValue();
	}
	
	
}
