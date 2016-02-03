package Univers;

import spaceship.*;

////////
// Classe conteneur de tous les objets de l'espace
//

// @TODO Ajouter les listes pour les autres objets
//       Méthode pour obtenir tous les objets dans une zone (avec temps de calcul réduit)
//		 Quand classe planete créée, ajouter fonction de création des objets planète en fonction d'un fichier texte
//
public class Espace {
	
	public Spaceship[] joueurs = new Spaceship[10];
	
	
	
	//Initialiser l'univers
	public void init(){
		
	}
	
	//Updater tous les objets de l'univers
	public void update(){
		
		//Updater les joueurs
		for(int i=0;i<joueurs.length;i++){
			if(joueurs[i]!=null){
				joueurs[i].update();
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
