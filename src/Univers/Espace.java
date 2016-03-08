package Univers;

import GENIA.Genia;
import display.Window;
import music.Music;
import spaceship.*;
import types.Vect3D;

////////
// Classe conteneur de tous les objets de l'espace
//

// @TODO Ajouter les listes pour les autres objets
//       Méthode pour obtenir tous les objets dans une zone (avec temps de calcul réduit)
//		 Quand classe planete créée, ajouter fonction de création des objets planète en fonction d'un fichier texte
//
public class Espace {
	
	public Spaceship[] joueurs = new Spaceship[10];
	public Window window;
	
	public Music sons;
	public Genia ia;
	
	
	public Espace()
	{
	}

	
	//Initialiser l'univers
	public void init(){
		
	}
	
	//Updater tous les objets de l'univers
	public void update(){
		
		//Updater les joueurs
		for(int i=0;i<joueurs.length;i++){
			if(joueurs[i]!=null){
<<<<<<< HEAD
				joueurs[i].update();
=======
				System.out.println(joueurs[i].externalSize);
				joueurs[i].update(); //joueurs[i].position.getPosition()
<<<<<<< HEAD
>>>>>>> 5f5cf2646abb8d34d7f1d3c11687fe35760ddab0
=======

>>>>>>> 866f28384fa77a9907b6eb0d5f74632a47b5dff1
				window.getDisplay().getCube(0).setPosition(joueurs[i].position.getPosition());
				window.getDisplay().getCube(0).setSize(new Vect3D(0.0001,0.0001,0.0001));
				
				joueurs[i].computeAbsolutePos();
				
				int j = 1;
				for(Module m: joueurs[0].modules){
					
					if(window.getDisplay().getCube(j) != null && m != null){			
						
						window.getDisplay().getCube(j).setPosition(m.AbsolutePosition);
						window.getDisplay().getCube(j).setSize(new Vect3D(m.rayon,m.rayon,m.rayon));
						
						j++;
					}
				}
			
			}
		}
		
	}	
	
	
	
	
	//////////////
	// JOUEURS
	//////////////
	
	//Ajouter un vaisseau joueur dans la liste des joueurs avec une id
	public void addPlayer(int id){
		
		if(id>=0 && id<10){
			joueurs[id] = new Spaceship(id);
			joueurs[id].myParent = this;
		}else{
			System.out.println("ID max du joueur : 10 (entré : "+id+")");
		}
		
	}
	//Obtenir le joueur avec son id
	public Spaceship getPlayer(int id){
		if(id>=0 && id<10 && joueurs[id]!=null){
			return joueurs[id];
		}else{
			System.out.println("ID du joueur innexistant (entré : "+id+")");
			return null;
		}
	}
	
	//////////////
	// FIN JOUEURS
	//////////////
	
	
}
