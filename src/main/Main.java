package main;

import Serveur.Serveur;
import maths.Vect3D;
import maths.VectRotation;

public class Main {
	
	public static String password="123";
	public static Serveur serveur;
	
	
	public static void main(String[] args) {
		
		System.out.println(VectRotation.matrixToIntrinsec(VectRotation.intrinsecToMatrix(new Vect3D(-1,4,0.5))).add(new Vect3D(-Math.PI,Math.PI+Math.PI,Math.PI)));
		
		serveur = Serveur.getServeur();
		
		
	}

}
