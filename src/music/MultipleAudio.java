package music;

import java.io.File;


//Permet de jouer en boucle une suite de sons
public class MultipleAudio {

	Audio[] audios;
	int position = 0;
	
	public MultipleAudio(String path){
		File folder = new File("assets/sounds/"+path);
		File[] listOfFiles = folder.listFiles();
		
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
			audios[position].gain(val);
		}
	}
	
	
	
}
