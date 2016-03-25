package Serveur;

import java.util.ArrayList;

public class Controller {
	public static boolean traiter(ArrayList<String> requetes){
		for (String requete : requetes) {
			System.out.println(requete);
		}
		return true;
	}
}
