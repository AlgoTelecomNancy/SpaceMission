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
	
	
	public Espace(Window window)
	{
		this.window = window;
	}

	
	//Initialiser l'univers
	public void init(){
		
	}
	
	//Updater tous les objets de l'univers
	public void update(){
		
		//Updater les joueurs
		for(int i=0;i<joueurs.length;i++){
			if(joueurs[i]!=null){
				System.out.println(joueurs[i].externalSize);
				joueurs[i].update();
				window.getDisplay().getCube(0).setPosition(joueurs[i].position.getPosition());
				window.getDisplay().getCube(0).setAngles(joueurs[i].orientation);
				window.getDisplay().getCube(0).setSize(new Vect3D(joueurs[i].externalSize, joueurs[i].externalSize, joueurs[i].externalSize));
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
