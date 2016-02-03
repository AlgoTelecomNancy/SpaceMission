package music;

import java.io.File;
import javax.swing.*;
import javax.sound.sampled.*;

public class Audio {

	//Sound me;
	private double lastStartTime;
	public boolean state;
	
	public Audio(String path){
		try{
			
		        Clip clip = AudioSystem.getClip();
		        // getAudioInputStream() also accepts a File or InputStream
		        AudioInputStream ais = AudioSystem.
		            getAudioInputStream( new File("assets/"+path) );
		        clip.open(ais);
		        clip.loop(Clip.LOOP_CONTINUOUSLY);
		        SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		                // A GUI element to prevent the Clip's daemon Thread
		                // from terminating at the end of the main()
		                JOptionPane.showMessageDialog(null, "Close to exit!");
		            }
		        });
		}
		catch(Exception exc){
		    System.out.println("Failed to play the file."+exc);
		}
		
		state = false;
		
	}
	
	public void start(){

		
		lastStartTime = System.currentTimeMillis()/1000;		
		state = true;
	}
	
	public double getTime(){
		return System.currentTimeMillis()/1000-lastStartTime;
	}
	
	public void stop(){
		state = false;
	}
	
	
}
