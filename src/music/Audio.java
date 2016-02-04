package music;

import java.io.File;
import javax.sound.sampled.*;

public class Audio {

	Clip clip;
	AudioInputStream me;
	boolean correct = true;
	String path = "";
	boolean playing;
	float megain = -80f;
	
	public long position = 0; // Position en microsecondes
	
	public Audio(String path){
		this.path = path;
		playing = false;

		try{				
	        clip = AudioSystem.getClip();
	        me = AudioSystem.getAudioInputStream( new File("assets/sounds/"+this.path) );
			clip = null;
			me= null; // vider la ram

		}catch(Exception exc){
		    System.out.println("Failed to load the file."+exc+this.path);
		    this.correct = false;
		}
	}
	
	//Ci dessous gestion de play pause...
	public void play(){
		try{				
	        clip = AudioSystem.getClip();
	        // getAudioInputStream() also accepts a File or InputStream
	        me = AudioSystem.getAudioInputStream( new File("assets/sounds/"+this.path) );
			clip.open(me);
			clip.setMicrosecondPosition(position);
			playing = true;
			this.gain(this.megain);
			clip.start();



		}catch(Exception exc){
		    System.out.println("Failed to play the file."+exc+this.path);
		    this.correct = false;
		    playing = false;
		}
	}
	
	public double getElapsedTime(){
		return (double)clip.getMicrosecondPosition()/1000000.0;
	}
	public double getTime(){
		return (double)clip.getMicrosecondLength()/1000000.0;
	}
	
	public void stop(){
		position = 0;
		clip.stop();
		clip = null;
		me = null;
		playing = false;
	}
	
	public void pause(){
		position = clip.getMicrosecondPosition();
		clip.stop();
		clip = null;
		me = null;
		playing = false;
	}
	
	//Ci dessous gestion du gain
	public void gain(float val){
	
		System.out.println(val + " " + this.path + " "+playing + " "+correct + " "+position);
		
		if(playing){
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			if(val>gainControl.getMaximum()){
				val = gainControl.getMaximum();
			}
			if(val<gainControl.getMinimum()){
				val = gainControl.getMinimum();
			}
	
			gainControl.setValue(val);
		}else{
			this.megain = val;
		}
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
	
	public void loop(boolean val){
		if(val){
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}else{
			clip.loop(1);
		}
	}
	
	
}
