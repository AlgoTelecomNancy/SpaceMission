package spaceship;

import java.io.*;
import java.util.regex.Pattern;

import Univers.*;
import spaceship.modules.*;
import types.*;


public class Spaceship {

	// Base
	public Position position = new Position();
	public Vect3D orientation = new Vect3D();
	public int myId = 0;

	public float horlogeExterne = 0; // Relativité restreinte
	public float tempsRelatif = 1; // Relativité restreinte
	public float masseRelative = 1; // Bon c'est de la vulgarisation, mais on
									// s'en sert directement pour le calcul de
									// la quantité de mouvemenet comme ça !

	public Vect3D vitesse = new Vect3D();
	public Vect3D acceleration = new Vect3D();
	public Vect3D vitesseRot = new Vect3D();
	public Vect3D accelerationRot = new Vect3D();

	public Module[] modules = new Module[200];
	public int nbModules = 0;

	public float masseTotale = 0;
	public Vect3D centreGravite = new Vect3D(); //La rotation se fait autour de ce point
	public double externalSize = 0; //Taille du point le plus éloigné du centre du vaisseau

	public Vect3D[] accelerations = new Vect3D[100]; // Au maximum 100
														// accélérations, et on
														// enlève les plus
														// faibles au fur et à
														// mesure si dépassement
	public Vect3D[] moments = new Vect3D[100]; // Au maximum 100 moments, et on
												// enlève les plus faibles au
												// fur et à mesure si
												// dépassement
	
	public Espace myParent;

	public Spaceship(int id) {
		this.myId = id;
		
		// Créer le module principal d'id 0 et de parent le vaisseau
		System.out.println("Initialisation du vaisseau, creation du module principal de commande.");
		modules[0] = new Command();

		// Ajouter les modules selon fichier de configuration
		addModules();

		// Mettre à jour le nombre de modules et leurs données
		for (int i = 0; i < modules.length; i++) {
			if (modules[i] != null) {
				nbModules++;
			
				//Initialiser des variables
				for(int j=0;j<modules[i].ArrayModulesBranche.length;j++){
					modules[i].ArrayModulesBranche[j] = -1;
				}
				for(int j=0;j<modules[i].ArrayModulesTouche.length;j++){
					modules[i].ArrayModulesTouche[j] = -1;
				}
				modules[i].myParent = this;
				modules[i].id = i;
			}
		}
		
		addLinks();
		addConnections();
		
		// Mettre à jour centre de gravité relatif au centre virtuel et masse
		// totale
		updateCentreGravite();
		///////////
		
		//Message de fin
		System.out.println("Initialisation du vaisseau termine.\n");
		

	}

	public void update() {

		

		// Vider l'array de vecteur accélération et moment
		accelerations = new Vect3D[accelerations.length];
		moments = new Vect3D[moments.length];

		// Mettre à jour les modules
		for (int i = 0; i < nbModules; i++) {
			modules[i].update();
		}
		//////////

		// Mise à jour de l'accélération totale et moment total
		acceleration.set(0, 0, 0);
		int i = 0;
		while (accelerations[i] != null && i < accelerations.length) {
			acceleration.x += accelerations[i].x;
			acceleration.y += accelerations[i].y;
			acceleration.z += accelerations[i].z;

			i++;
		}
		accelerationRot.set(0, 0, 0);
		i = 0;
		while (moments[i] != null && i < moments.length) {
			accelerationRot.x += moments[i].x;
			accelerationRot.y += moments[i].y;
			accelerationRot.z += moments[i].z;

			i++;
		}
		///////

		// Mise à jour vitesse
		vitesse.translate(acceleration.multiply(base.Cons.universalDeltaTime));
		vitesseRot.translate(accelerationRot.multiply(base.Cons.universalDeltaTime));
		vitesseRot.modulo(360); // Pour éviter de trop grands nombres
		//////

		// Mise à jour de la position avec la vitesse
		position.translate(vitesse.multiply(base.Cons.universalDeltaTime));
		orientation.translate(vitesseRot.multiply(base.Cons.universalDeltaTime));
		orientation.modulo(360);// Pour éviter de trop grands nombres

		// Relativité et horloge
		tempsRelatif = (float) (1 / Math.sqrt(1 - Math.pow(vitesse.size() / 299792, 2)));
		masseRelative = (float) (1 / Math.sqrt(0.97 - Math.min(Math.pow((vitesse.size()) / 299792, 0.9), 0.9699999)));
		horlogeExterne += base.Cons.deltaTime * tempsRelatif;

		verification();
		
		for(int j=0; j<nbModules; j++){
			System.out.print("[ Module "+j+" : "+" nbH = " +modules[i].nbHumains + " ] ; ");
		}
		System.out.println("");
		
	}

	private void verification() {
		// Dépassement de vitesse maximum (0.99*c)
		if (vitesse.size() > 296000) {
			vitesse = vitesse.multiply(296000 / vitesse.size());
		}
	}

	private void addModules() {
		
		
		System.out.println("Creation du vaisseau, ajout des modules");

		String[] separ;
		double masse;
		double size;
		double x;
		double y;
		double z;
		double rx;
		double ry;
		double rz;
		String[] options;
		int id = 0;
		

		try {
			InputStream ips = new FileInputStream("assets/story/spaceship/parts.game");
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {

				separ = ligne.split("\\|");
				if (separ.length == 10) {

					id = 0;
					while(id<modules.length && modules[id] != null){
						id++;
					}
					masse = Double.parseDouble(separ[1]);
					size = Double.parseDouble(separ[2]);
					x = Double.parseDouble(separ[3]);
					y = Double.parseDouble(separ[4]);
					z = Double.parseDouble(separ[5]);
					rx = Double.parseDouble(separ[6]);
					ry = Double.parseDouble(separ[7]);
					rz = Double.parseDouble(separ[8]);
					options = separ[9].split(",");
					
					
					System.out.println(" -> Ajout d'un module de type " + separ[0] + " (id = " + id + ")");

					switch (separ[0]) {
			            case "Battery": modules[id] = new Battery(options);
			                     break;
			            case "Canon": modules[id] = new Canon(options);
	                     		break;
			            case "Engine": modules[id] = new Engine(options);
			            		break;
			            case "Laser": modules[id] = new Laser(options);
			            		break;
			            case "Radar": modules[id] = new Radar(options);
			            		break;
			            case "Shield": modules[id] = new Shield(options);
                 				break;
			            case "SolarPanels": modules[id] = new SolarPanels(options);
         						break;
			            case "Storage": modules[id] = new Storage(options);
         						break;
			            case "VSL": modules[id] = new VSL(options);
         						break;
			            default: modules[id] = new Room(options);
			                     break;
			        }
					
					modules[id].poids = (float) masse;
					modules[id].rayon = (float) size;
					modules[id].position.set(x, y, z);
					modules[id].orientation.set(rx, ry, rz);
					

				} else {
					System.out.println(
							" /!\\ Ligne invalide lors de la lecture du modèle du vaisseau, " + separ.length + " éléments");
				}

			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	

	private void addLinks() {

		System.out.println("Ajout des liens physiques entre modules");

		String[] separ;
		int length;
		int i;
		int j;
		
		try {
			InputStream ips = new FileInputStream("assets/story/spaceship/links.game");
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {

				separ = ligne.split("\\|");
				if (separ.length == 2) {
					
					length = modules[(int)Float.parseFloat(separ[0])].ArrayModulesTouche.length;
					i = 0;
					while(i<length && modules[(int)Float.parseFloat(separ[0])].ArrayModulesTouche[i] != -1){
						i++;
					}
					j = 0;
					while(j<length && modules[(int)Float.parseFloat(separ[1])].ArrayModulesTouche[j] != -1){
						j++;
					}
					if(i<length && j<length){
						modules[(int)Float.parseFloat(separ[0])].ArrayModulesTouche[i] = (int)Float.parseFloat(separ[1]);
						modules[(int)Float.parseFloat(separ[1])].ArrayModulesTouche[j] = (int)Float.parseFloat(separ[0]);
						
						System.out.println(" -> Ajout d'un lien entre les modules " + separ[0] + " et " + separ[1]);

					}else{
						System.out.println(" /!\\ Erreur, aucun lien ajoute entre " + separ[0] + " et " + separ[1]);
					}

				} else {
					System.out.println(
							" /!\\ Ligne invalide lors de la lecture des liens du vaisseau, " + separ.length + " éléments");
				}

			}
			br.close();
		} catch (Exception e) {
			System.out.println(" /!\\ Le fichier links est mal construit, des liens ont ete ignores...");
		}
	}
	
	private void addConnections() {

		System.out.println("Ajout des branchements entre modules");

		String[] separ;
		int length;
		int i;
		int j;
		
		try {
			InputStream ips = new FileInputStream("assets/story/spaceship/connections.game");
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {

				separ = ligne.split("\\|");
				if (separ.length == 2) {
					
					length = modules[(int)Float.parseFloat(separ[0])].ArrayModulesBranche.length;
					i = 0;
					while(i<length && modules[(int)Float.parseFloat(separ[0])].ArrayModulesBranche[i] != -1){
						i++;
					}
					j = 0;
					while(j<length && modules[(int)Float.parseFloat(separ[1])].ArrayModulesBranche[j] != -1){
						j++;
					}
					if(i<length && j<length){
						modules[(int)Float.parseFloat(separ[0])].ArrayModulesBranche[i] = (int)Float.parseFloat(separ[1]);
						modules[(int)Float.parseFloat(separ[1])].ArrayModulesBranche[j] = (int)Float.parseFloat(separ[0]);
						
						System.out.println(" -> Ajout d'un branchements entre les modules " + separ[0] + " et " + separ[1]);

					}else{
						System.out.println(" /!\\ Erreur, aucun branchements ajoute entre " + separ[0] + " et " + separ[1]);
					}

				} else {
					System.out.println(
							" /!\\ Ligne invalide lors de la lecture des branchements du vaisseau, " + separ.length + " éléments");
				}

			}
			br.close();
		} catch (Exception e) {
			System.out.println(" /!\\ Le fichier connections est mal construit, des branchements ont ete ignores...");
		}
	}
	
	private void updateCentreGravite(){
		masseTotale = 0;
		for (int i = 0; i < nbModules; i++) {
			masseTotale += modules[i].poids;
		}
		centreGravite.set(0, 0, 0);
		for (int i = 0; i < nbModules; i++) {
			centreGravite.x += modules[i].position.x * modules[i].poids;
			centreGravite.y += modules[i].position.y * modules[i].poids;
			centreGravite.z += modules[i].position.z * modules[i].poids;
		}
		centreGravite.x = centreGravite.x / masseTotale;
		centreGravite.y = centreGravite.y / masseTotale;
		centreGravite.z = centreGravite.z / masseTotale;
		
		//Mettre à jour la position des modules
		for (int i = 0; i < nbModules; i++) {
			modules[i].updatePositionRel(centreGravite);
		}
		
		//Mise a jour de la taille globale
		//externalSize
		externalSize = 0;
		for (int i = 0; i < nbModules; i++) {
			if(modules[i].positionRelative.size()+modules[i].rayon>externalSize){
				externalSize = modules[i].positionRelative.size()+modules[i].rayon;
			}
		}
		
	}

}
