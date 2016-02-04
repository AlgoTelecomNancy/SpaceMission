package music;

import java.io.File;
import java.util.*;


//Permet de jouer en boucle une suite de sons
public class MultipleAudio {

	Audio[] audios;
	int position = 0;
	
	private boolean fadein = false;
	private boolean fadeout = false;

	public MultipleAudio(String path){
		File folder = new File("assets/sounds/"+path);
		File[] listOfFiles = folder.listFiles();

		ShuffleArray(listOfFiles);
		
		this.audios = new Audio[listOfFiles.length];
		
		int p = 0;
	    for (int i = 0; i < Math.min(1000, listOfFiles.length); i++) {
	      if (listOfFiles[i].isFile()) {
	    	  this.audios[p] =  new Audio(path+"/"+listOfFiles[i].getName());
	    	  if(this.audios[p].correct==true){
	    		  p++; 
	    	  }else{
	    		  this.audios[p] = null;
	    	  }
	      }
	    }
	}
	
	private void ShuffleArray(File[] array)
	{
	    int index;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        if (index != i)
	        {
	            array[index] = array[i];
	            array[i] = array[index];
	            array[index] = array[i];
	        }
	    }
	}
	
	public void update(){
		
		if(audios[position].getTime()-audios[position].getElapsedTime()<5){
			audios[position].gain((float) (audios[position].gain()-3*base.Cons.deltaTime));
			int next = position+1;
			if(audios[next]==null){
				next = 0;
			}
			if(audios[next].playing == false){
				audios[next].play();
			}
			audios[next].gain((float) Math.max(0,audios[next].gain()+3*base.Cons.deltaTime));
			if(audios[position].getTime()-audios[position].getElapsedTime()<1){
				audios[position].stop();
				position = next;
			}
		}
		
		if(fadeout){
			this.gain(Math.min(-40f,(float)(this.gain()-base.Cons.deltaTime*2)));
		}
		if(fadein){
			this.gain(Math.max(0f,(float)(this.gain()+base.Cons.deltaTime*2)));
		}
		if(this.gain()==0){
			this.fadein = false;
		}
		if(this.gain()==-40){
			this.fadeout = false;
		}
		
	}
	
	public void play(){
		audios[position].play();
	}
	public void pause(){
		audios[position].pause();
	}
	public void stop(){
		audios[position].stop();
		position = 0;
	}
	public float gain(){
		return audios[0].gain();
	}
	public void gain(float val){
		for(int i=0;i<audios.length;i++){
			if(audios[i]!=null && audios[i].playing==true){
				audios[i].gain(val);
			}
		}
	}
	
	public float ending(){
		return (float)(audios[position].getTime()-audios[position].getElapsedTime());
	}
	
	
	public void fadeIn(int temps){
		fadein = true;
		fadeout = false;
	}
	
	public void fadeOut(int temps){
		fadein = false;
		fadeout = true;
	}
	
}
